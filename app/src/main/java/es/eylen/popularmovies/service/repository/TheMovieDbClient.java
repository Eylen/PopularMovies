package es.eylen.popularmovies.service.repository;

import es.eylen.popularmovies.service.model.TheMovieDBResponse;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by eylen on 16/02/2018.
 */

public interface TheMovieDbClient {
    String API_URL = "https://api.themoviedb.org/3/";


    @GET("movie/popular")
    Call<TheMovieDBResponse> getPopularMovies();

    @GET("movie/top_rated")
    Call<TheMovieDBResponse> getTopRatedMovies();
}
