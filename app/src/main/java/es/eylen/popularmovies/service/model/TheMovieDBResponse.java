package es.eylen.popularmovies.service.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * TheMovieDB API response model holder
 */
public class TheMovieDBResponse {
    private int page;
    @SerializedName(value = "total_results")
    private int totalResults;
    @SerializedName(value = "total_pages")
    private int totalPages;
    @SerializedName(value = "results")
    private List<Movie> movies;

    public TheMovieDBResponse() {
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
