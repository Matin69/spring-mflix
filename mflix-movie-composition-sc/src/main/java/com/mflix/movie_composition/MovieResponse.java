package com.mflix.movie_composition;

public class MovieResponse {

    private final Object movie;

    private final Object comments;

    public MovieResponse(Object movie, Object comments) {
        this.movie = movie;
        this.comments = comments;
    }

    public Object getMovie() {
        return movie;
    }

    public Object getComments() {
        return comments;
    }
}
