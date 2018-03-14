package es.eylen.popularmovies.service.repository.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import es.eylen.popularmovies.service.model.Movie;

/**
 * TheMovieDB API response model holder
 */
public class MoviesResponse {
    private int page;
    @SerializedName(value = "total_results")
    private int totalResults;
    @SerializedName(value = "total_pages")
    private int totalPages;
    @SerializedName(value = "results")
    private List<Movie> movies;

    public MoviesResponse() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
