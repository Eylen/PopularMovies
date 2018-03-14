package es.eylen.popularmovies.service.repository.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import es.eylen.popularmovies.service.model.Trailer;

/**
 * TheMovieDB movie trailers API response model holder
 */
public class MovieTrailersResponse {
    private int id;
    @SerializedName(value = "results")
    private List<Trailer> trailers;

    public MovieTrailersResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }
}
