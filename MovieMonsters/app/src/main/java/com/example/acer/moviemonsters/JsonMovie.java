package com.example.acer.moviemonsters;

import android.os.Parcel;
import android.os.Parcelable;

public class JsonMovie implements Parcelable {

    public static final Creator<JsonMovie> CREATOR = new Creator<JsonMovie>() {
        @Override
        public JsonMovie createFromParcel(Parcel in) {
            return new JsonMovie(in);
        }

        @Override
        public JsonMovie[] newArray(int size) {
            return new JsonMovie[size];
        }
    };
    int id, vote_count;
    boolean video, adult;
    double vote_average;
    long popularity;
    String title;
    String poster_path;
    String original_language;
    String original_title;
    String backdrop_path;
    String overview;
    String release_date;

    JsonMovie(int vote_count, int id, double vote_average, String poster_path,
              String original_language, String original_title, String backdrop_path,
              String overview, String release_date) {
        this.vote_average = vote_average;
        this.id = id;
        this.vote_count = vote_count;
        this.poster_path = poster_path;
        this.original_language = original_language;
        this.original_title = original_title;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.release_date = release_date;
    }

    protected JsonMovie(Parcel in) {
        id = in.readInt();
        vote_count = in.readInt();
        vote_average = in.readDouble();
        poster_path = in.readString();
        original_language = in.readString();
        original_title = in.readString();
        backdrop_path = in.readString();
        overview = in.readString();
        release_date = in.readString();
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public void setPopularity(long popularity) {
        this.popularity = popularity;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(vote_count);
        dest.writeDouble(vote_average);
        dest.writeString(poster_path);
        dest.writeString(original_language);
        dest.writeString(original_title);
        dest.writeString(backdrop_path);
        dest.writeString(overview);
        dest.writeString(release_date);
    }
}
