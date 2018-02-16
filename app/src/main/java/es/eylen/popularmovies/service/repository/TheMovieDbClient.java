package es.eylen.popularmovies.service.repository;

import java.util.List;

import es.eylen.popularmovies.service.model.Movie;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by eylen on 16/02/2018.
 */

public interface TheMovieDbClient {
    String API_URL = "https://api.themoviedb.org/3/";


    @GET("movie/popular")
    Call<List<Movie>> getPopularMovies();

    @GET("movie/top_rated")
    Call<List<Movie>> getTopRatedMovies();
}
