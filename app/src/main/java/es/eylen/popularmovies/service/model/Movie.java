package es.eylen.popularmovies.service.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by eylen on 16/02/2018.
 */

public class Movie {
    private int id;
    @SerializedName(value = "vote_count")
    private int voteCount;
    @SerializedName(value = "vote_average")
    private float voteAverage;
    private float popularity;

    private String title;
    @SerializedName(value = "original_title")
    private String originalTitle;
    @SerializedName(value = "poster_path")
    private String poster;

    @SerializedName(value = "overview")
    private String synopsis;
    @SerializedName(value = "release_date")
    private Date releaseDate;

    public Movie() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
}
