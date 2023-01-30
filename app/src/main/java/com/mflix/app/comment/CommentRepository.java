package com.mflix.app.comment;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

    @Query("{ $or : [ { $expr: { $eq: ['?0', 'null'] } } , { movie_id : ObjectId(?0) } ] }")
    List<Comment> search(String movieId, Pageable pageable);
}