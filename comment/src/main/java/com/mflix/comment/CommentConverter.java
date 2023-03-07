package com.mflix.comment;

import com.mflix.annotation.ResponseConverter;
import com.mflix.core.common.Converter;

@ResponseConverter
public class CommentConverter implements Converter {

    @Override
    public Object convert(Object obj) {
        Comment comment = ((Comment) obj);
        return new CommentResponse(
                comment.id,
                comment.name,
                comment.email,
                comment.movieId,
                comment.text,
                comment.date
        );
    }

    @Override
    public boolean supports(Object object) {
        return object instanceof Comment;
    }
}
