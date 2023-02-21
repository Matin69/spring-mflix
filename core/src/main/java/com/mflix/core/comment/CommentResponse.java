package com.mflix.core.comment;

import java.util.Date;

public class CommentResponse {

    public String id;

    public String name;

    public String email;

    public String movieId;

    public String text;

    public Date date;

    public CommentResponse(String id, String name, String email, String movieId, String text, Date date) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.movieId = movieId;
        this.text = text;
        this.date = date;
    }
}
