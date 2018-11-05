package com.swat_cat.firstapp.data.models;

import com.google.gson.annotations.SerializedName;

public class Movie {

    private String type;

    private Integer year;

    private String imdbID;

    private String poster;

    private String title;

    public Movie(String type, Integer year, String imdbID, String poster, String title) {
        this.type = type;
        this.year = year;
        this.imdbID = imdbID;
        this.poster = poster;
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
