package com.mflix.app.comment;

import com.mflix.app.common.AbstractQueryParams;

public class CommentQueryParams extends AbstractQueryParams {

    private String movieId;

    private String email;

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
