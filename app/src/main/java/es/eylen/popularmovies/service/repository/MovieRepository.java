package es.eylen.popularmovies.service.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import es.eylen.popularmovies.BuildConfig;
import es.eylen.popularmovies.service.helper.network.Resource;
import es.eylen.popularmovies.service.helper.network.Status;
import es.eylen.popularmovies.service.model.Movie;
import es.eylen.popularmovies.service.model.TheMovieDBResponse;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by eylen on 16/02/2018.
 */

public class MovieRepository {
    private TheMovieDbClient movieDbService;
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
        Call<TheMovieDBResponse> call;
        if (sortByPopularity) {
            call = movieDbService.getPopularMovies();
        } else {
            call = movieDbService.getTopRatedMovies();
        }

        call.enqueue(new Callback<TheMovieDBResponse>() {
            @Override
            public void onResponse(Call<TheMovieDBResponse> call, Response<TheMovieDBResponse> response) {
                Resource<List<Movie>> result;
                if (response.isSuccessful()) {
                    result = new Resource<>(Status.SUCCESS, response.body().getMovies(), "");
                } else {
                    //TODO implement failure handling
                    result = new Resource<>(Status.ERROR, null, response.message());
                }
                mutableResponse.postValue(result);
            }

            @Override
            public void onFailure(Call<TheMovieDBResponse> call, Throwable t) {
                mutableResponse.postValue(new Resource<>(Status.ERROR, null, t.getMessage()));
            }
        });
        return mutableResponse;
    }
}
