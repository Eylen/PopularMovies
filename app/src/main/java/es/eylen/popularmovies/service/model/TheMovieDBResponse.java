package es.eylen.popularmovies.service.model;

import java.util.List;

/**
 * Created by spicado on 19/02/2018.
 */

public class TheMovieDBResponse {
    private int page;
    private int totalResults;
    private int totalPages;
    private List<Movie> results;

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

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
