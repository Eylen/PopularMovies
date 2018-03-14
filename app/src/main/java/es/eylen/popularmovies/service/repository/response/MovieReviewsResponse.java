package es.eylen.popularmovies.service.repository.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import es.eylen.popularmovies.service.model.Review;

/**
 * TheMovieDB API response model holder
 */
public class MovieReviewsResponse {
    private int id;
    private int page;
    @SerializedName(value = "total_results")
    private int totalResults;
    @SerializedName(value = "total_pages")
    private int totalPages;
    @SerializedName(value = "results")
    private List<Review> reviews;

    public MovieReviewsResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
