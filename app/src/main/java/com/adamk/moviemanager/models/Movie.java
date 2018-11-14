package com.adamk.moviemanager.models;

import java.io.Serializable;

public class Movie implements Serializable {
    private String id;
    private String title;
    private String overview;
    private String releaseDate;
    private String voteAverage;
    private String voteCount;
    private String posterPath;
    private String backdropPath;

    public Movie(String id, String title, String overview, String releaseDate, String voteAverage,
                 String voteCount, String posterPath, String backdropPath) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getReleaseYear() {
        if (releaseDate == null) return "";
        String[] splitDate = releaseDate.split("-"); // Format of 2018-10-31
        return splitDate[0];
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w500%s", posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/original%s", backdropPath);
    }

}
