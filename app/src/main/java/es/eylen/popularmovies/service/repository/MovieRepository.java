package es.eylen.popularmovies.service.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.eylen.popularmovies.BuildConfig;
import es.eylen.popularmovies.data.MoviesContract;
import es.eylen.popularmovies.service.helper.network.Resource;
import es.eylen.popularmovies.service.model.Movie;
import es.eylen.popularmovies.service.model.Review;
import es.eylen.popularmovies.service.model.Trailer;
import es.eylen.popularmovies.service.repository.response.MovieReviewsResponse;
import es.eylen.popularmovies.service.repository.response.MovieTrailersResponse;
import es.eylen.popularmovies.service.repository.response.MoviesResponse;
import es.eylen.popularmovies.utils.Constants;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Central Movie repository for application
 */
public class MovieRepository {
    private final TheMovieDbClient movieDbService;
    private static MovieRepository movieRepository;

    private ContentResolver mContentResolver;

    private MovieRepository(Context context){
        //NOTE: Interceptor for adding API key to all requests: https://futurestud.io/tutorials/retrofit-2-how-to-add-query-parameters-to-every-request
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();

            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", BuildConfig.MOVIE_DB_API_KEY)
                    .build();

            // Request customization: add request headers
            Request.Builder requestBuilder = original.newBuilder()
                    .url(url);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TheMovieDbClient.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        movieDbService = retrofit.create(TheMovieDbClient.class);

        mContentResolver = context.getContentResolver();
    }

    public synchronized static MovieRepository getInstance(Context context){
        if (movieRepository == null){
            movieRepository = new MovieRepository(context);
        }
        return movieRepository;
    }

    public LiveData<Resource<List<Movie>>> loadMovieList(String filterSortOption){
        MutableLiveData<Resource<List<Movie>>> mutableResponse = new MutableLiveData<>();
        mutableResponse.postValue(Resource.loading(null));
        Call<MoviesResponse> call;
        switch (filterSortOption){
            case Constants.SORT_BY_POPULARITY:
                call = movieDbService.getPopularMovies();
                break;
            case Constants.SORT_BY_TOP_RATED:
                call = movieDbService.getTopRatedMovies();
                break;
            default:
                call = null;
        }

        if (call != null) {
            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                    Resource<List<Movie>> result;
                    if (response.isSuccessful()) {
                        List<Movie> movieList = (response.body() != null) ? response.body().getMovies() : null;
                        addToContentProvider(movieList);
                        result = Resource.success(movieList);
                    } else {
                        //Retrieve from database
                        result = Resource.success(fetchFromContentProvider(filterSortOption));
                        //result = Resource.error(response.message(), null);
                    }
                    mutableResponse.postValue(result);
                }

                @Override
                public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                    //mutableResponse.postValue(Resource.error(t.getMessage(), null));
                    mutableResponse.postValue(Resource.success(fetchFromContentProvider(filterSortOption)));
                }
            });
        } else {
            mutableResponse.postValue(Resource.success(fetchFromContentProvider(filterSortOption)));
        }
        return mutableResponse;
    }

    public LiveData<Resource<List<Trailer>>> loadMovieTrailers(int movieId){
        MutableLiveData<Resource<List<Trailer>>> mutableResponse = new MutableLiveData<>();
        mutableResponse.postValue(Resource.loading(null));

        Call<MovieTrailersResponse> call = movieDbService.getMovieTrailers(movieId);
        call.enqueue(new Callback<MovieTrailersResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieTrailersResponse> call, @NonNull Response<MovieTrailersResponse> response) {
                Resource<List<Trailer>> result;
                if (response.isSuccessful()){
                    result = Resource.success(response.body().getTrailers());
                } else {
                    result = Resource.error(response.message(), null);
                }
                mutableResponse.postValue(result);
            }

            @Override
            public void onFailure(@NonNull Call<MovieTrailersResponse> call, @NonNull Throwable t) {
                mutableResponse.postValue(Resource.error(t.getMessage(), null));
            }
        });
        return mutableResponse;
    }

    public LiveData<Resource<List<Review>>> loadMovieReviews(int movieId){
        MutableLiveData<Resource<List<Review>>> mutableResponse = new MutableLiveData<>();
        mutableResponse.postValue(Resource.loading(null));

        Call<MovieReviewsResponse> call = movieDbService.getMovieReviews(movieId);
        call.enqueue(new Callback<MovieReviewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieReviewsResponse> call, @NonNull Response<MovieReviewsResponse> response) {
                Resource<List<Review>> result;
                if (response.isSuccessful()){
                    result = Resource.success(response.body().getReviews());
                } else {
                    result = Resource.error(response.message(), null);
                }
                mutableResponse.postValue(result);
            }

            @Override
            public void onFailure(@NonNull Call<MovieReviewsResponse> call, @NonNull Throwable t) {
                mutableResponse.postValue(Resource.error(t.getMessage(), null));
            }
        });
        return mutableResponse;
    }

    public boolean loadMovieFavoriteState(int movieId){
        String[] projection = new String[]{};

        Cursor cursor = mContentResolver.query(MoviesContract.MovieEntry.CONTENT_URI.buildUpon().appendPath(String.valueOf(movieId)).build(),
                projection, null, null, null);
        boolean isFavorite = false;
        if (cursor != null) {
            if (cursor.moveToNext()) {
                isFavorite = cursor.getInt(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_FAVORITE)) == 1;
            }

            cursor.close();
        }
        return isFavorite;
    }

    public void updateMovieFavoriteState(int movieId, boolean isFavorite){
        ContentValues values = new ContentValues();
        values.put(MoviesContract.MovieEntry.COLUMN_FAVORITE, isFavorite?1:0);
        String where = MoviesContract.MovieEntry.COLUMN_ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(movieId)};
        mContentResolver.update(MoviesContract.MovieEntry.CONTENT_URI.buildUpon().appendPath(String.valueOf(movieId)).build(),
                values, where, selectionArgs);
    }

    private void addToContentProvider(List<Movie> movieList){
        ContentValues[] contentValues = new ContentValues[movieList.size()];
        ContentValues values;
        Movie movie;
        for (int i = 0; i < movieList.size(); i++){
            movie = movieList.get(i);
            values = new ContentValues();
            values.put(MoviesContract.MovieEntry.COLUMN_ID, movie.getId());
            values.put(MoviesContract.MovieEntry.COLUMN_ORIGINAL_TITLE, movie.getOriginalTitle());
            values.put(MoviesContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
            values.put(MoviesContract.MovieEntry.COLUMN_POPULARITY, movie.getPopularity());
            values.put(MoviesContract.MovieEntry.COLUMN_POSTER_PATH, movie.getPoster());
            values.put(MoviesContract.MovieEntry.COLUMN_BACKDROP_PATH, movie.getBackdrop());
            values.put(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate().getTime()/1000);//Stored as seconds
            values.put(MoviesContract.MovieEntry.COLUMN_SYNOPSIS, movie.getSynopsis());
            values.put(MoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
            values.put(MoviesContract.MovieEntry.COLUMN_VOTE_COUNT, movie.getVoteCount());
            contentValues[i] = values;
        }
        mContentResolver.bulkInsert(MoviesContract.MovieEntry.CONTENT_URI, contentValues);
    }

    private List<Movie> fetchFromContentProvider(String filterSortOption){
        List<Movie> movieList = new ArrayList<>();
        String[] projection = new String[]{};
        String selection = null;
        String[] selectionArgs = new String[]{};
        String sortOrder = null;
        if (Constants.FILTER_BY_FAVORITES.equals(filterSortOption)){
            selection = MoviesContract.MovieEntry.COLUMN_FAVORITE + " = ?";
            selectionArgs = new String[]{String.valueOf(1)};
        } else {
            sortOrder = (Constants.SORT_BY_POPULARITY.equals(filterSortOption)?MoviesContract.MovieEntry.COLUMN_POPULARITY: MoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE) + " DESC";
        }

        Cursor cursor = mContentResolver.query(MoviesContract.MovieEntry.CONTENT_URI, projection, selection, selectionArgs, sortOrder);
        while (cursor.moveToNext()){
            Movie movie = new Movie();
            movie.setId(cursor.getInt(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_ID)));
            movie.setOriginalTitle(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_ORIGINAL_TITLE)));
            movie.setTitle(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_TITLE)));
            movie.setPoster(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_POSTER_PATH)));
            movie.setBackdrop(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_BACKDROP_PATH)));
            movie.setPopularity(cursor.getFloat(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_POPULARITY)));
            movie.setReleaseDate(new Date(cursor.getInt(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE)) * 1000));
            movie.setSynopsis(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_SYNOPSIS)));
            movie.setVoteAverage(cursor.getFloat(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE)));
            movie.setVoteCount(cursor.getInt(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_VOTE_COUNT)));
            movieList.add(movie);
        }
        cursor.close();
        return movieList;
    }
}
