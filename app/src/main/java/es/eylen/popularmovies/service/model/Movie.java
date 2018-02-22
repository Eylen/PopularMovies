package es.eylen.popularmovies.service.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Date;

/**
 * Movie model class
 */
public class Movie implements Parcelable{
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
    private int year;

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
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.releaseDate);
        this.year = calendar.get(Calendar.YEAR);
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", voteCount=" + voteCount +
                ", voteAverage=" + voteAverage +
                ", popularity=" + popularity +
                ", title='" + title + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", poster='" + poster + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    private Movie(Parcel parcel){
        this.id = parcel.readInt();
        this.voteCount = parcel.readInt();
        this.voteAverage = parcel.readFloat();
        this.popularity = parcel.readFloat();
        this.title = parcel.readString();
        this.originalTitle = parcel.readString();
        this.poster = parcel.readString();
        this.synopsis = parcel.readString();
        this.releaseDate = new Date(parcel.readLong());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.releaseDate);
        this.year = calendar.get(Calendar.YEAR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(voteCount);
        parcel.writeFloat(voteAverage);
        parcel.writeFloat(popularity);
        parcel.writeString(title);
        parcel.writeString(originalTitle);
        parcel.writeString(poster);
        parcel.writeString(synopsis);
        parcel.writeLong(releaseDate.getTime());
    }
}
