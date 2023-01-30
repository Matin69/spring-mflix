package com.mflix.app.comment;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Document("comments")
public class Comment {

    @MongoId(FieldType.OBJECT_ID)
    public String id;

    public String name;

    public String email;

    @Field(targetType = FieldType.OBJECT_ID, name = "movie_id")
    public String movieId;

    public String text;

    public Date date;

    public Comment() {
    }

    public Comment(String name, String email, String movieId, String text, Date date) {
        this(null, name, email, movieId, text, date);
    }

    public Comment(String id, String name, String email, String movieId, String text, Date date) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.movieId = movieId;
        this.text = text;
        this.date = date;
    }
}
