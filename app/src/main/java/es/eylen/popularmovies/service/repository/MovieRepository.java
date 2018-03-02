package es.eylen.popularmovies.service.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import es.eylen.popularmovies.BuildConfig;
import es.eylen.popularmovies.service.helper.network.Resource;
import es.eylen.popularmovies.service.model.Movie;
import es.eylen.popularmovies.service.model.Review;
import es.eylen.popularmovies.service.model.Trailer;
import es.eylen.popularmovies.service.repository.response.MovieReviewsResponse;
import es.eylen.popularmovies.service.repository.response.MovieTrailersResponse;
import es.eylen.popularmovies.service.repository.response.MoviesResponse;
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

    private MovieRepository(){
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
    }

    public synchronized static MovieRepository getInstance(){
        if (movieRepository == null){
            movieRepository = new MovieRepository();
        }
        return movieRepository;
    }

    public LiveData<Resource<List<Movie>>> loadMovieList(boolean sortByPopularity){
        MutableLiveData<Resource<List<Movie>>> mutableResponse = new MutableLiveData<>();
        mutableResponse.postValue(Resource.loading(null));
        Call<MoviesResponse> call;
        if (sortByPopularity) {
            call = movieDbService.getPopularMovies();
        } else {
            call = movieDbService.getTopRatedMovies();
        }

        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                Resource<List<Movie>> result;
                if (response.isSuccessful()) {
                    result = Resource.success((response.body() != null) ? response.body().getMovies() : null);
                } else {
                    result = Resource.error(response.message(), null);
                }
                mutableResponse.postValue(result);
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                mutableResponse.postValue(Resource.error(t.getMessage(), null));
            }
        });
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
}
