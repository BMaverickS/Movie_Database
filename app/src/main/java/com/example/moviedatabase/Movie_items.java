package com.example.moviedatabase;

public class Movie_items {
    private String moviename;
    private String movieimage;
    private String movieyear;
    private String movieid;

    public Movie_items(String moviename, String movieimage, String movieyear, String movieid)
    {
        this.moviename = moviename;
        this.movieimage = movieimage;
        this.movieyear = movieyear;
        this.movieid = movieid;
    }

    public String getMoviename() {
        return moviename;
    }

    public String getMovieimage() {
        return movieimage;
    }

    public String getMovieyear() {
        return movieyear;
    }

    public String getMovieid() {
        return movieid;
    }
}
