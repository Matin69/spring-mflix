package com.mflix.app.comment;

import com.mflix.app.common.AbstractQueryParams;

public class CommentQueryParams extends AbstractQueryParams {

    private String movieId;

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
}
