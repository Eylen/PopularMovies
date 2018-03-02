package es.eylen.popularmovies.service.repository;

import es.eylen.popularmovies.service.repository.response.MovieReviewsResponse;
import es.eylen.popularmovies.service.repository.response.MovieTrailersResponse;
import es.eylen.popularmovies.service.repository.response.MoviesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * TheMovieDB API client for Retrofit2
 */
public interface TheMovieDbClient {
    String API_URL = "https://api.themoviedb.org/3/";


    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies();

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies();

    @GET("movie/{id}/videos")
    Call<MovieTrailersResponse> getMovieTrailers(@Path("id") int id);

    @GET("movie/{id}/reviews")
    Call<MovieReviewsResponse> getMovieReviews(@Path("id") int id);
}
