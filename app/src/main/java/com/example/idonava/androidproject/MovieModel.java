package com.example.idonava.androidproject;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MovieModel implements Parcelable {
    int mainImage;
    int backgroundImage;
    @PrimaryKey
    int id;
    double popularity;
    String movieTitle;
    String date;
    String overview;
    String trailerUrl;
    String posterPath;
    public static final String urlImagePath="http://image.tmdb.org/t/p/w185/";
    public MovieModel() {

    }


    protected MovieModel(Parcel in) {
        mainImage = in.readInt();
        backgroundImage = in.readInt();
        id = in.readInt();
        popularity = in.readDouble();
        movieTitle = in.readString();
        date = in.readString();
        overview = in.readString();
        trailerUrl = in.readString();
        posterPath = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mainImage);
        dest.writeInt(backgroundImage);
        dest.writeInt(id);
        dest.writeDouble(popularity);
        dest.writeString(movieTitle);
        dest.writeString(date);
        dest.writeString(overview);
        dest.writeString(trailerUrl);
        dest.writeString(posterPath);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMainImage() {
        return mainImage;
    }

    public void setMainImage(int mainImage) {
        this.mainImage = mainImage;
    }

    public int getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(int backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getPosterPath() {
        return posterPath;
    }
    public String getPosterPathUri() {
        return urlImagePath+posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    @Override
    public String toString() {
        return "MovieModel{" +
                "mainImage=" + mainImage +
                ", backgroundImage=" + backgroundImage +
                ", movieTitle='" + movieTitle + '\'' +
                ", date='" + date + '\'' +
                ", overview='" + overview + '\'' +
                ", trailerUrl='" + trailerUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieModel)) return false;
        MovieModel that = (MovieModel) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }
}
