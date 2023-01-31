package com.mflix.app.comment;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

    @Query("{ $and : [ { $or : [ { $expr: { $eq: ['?1', 'null'] } } , { email : ?1 } ] } ] }")
    List<Comment> search(String movieId, String email, Pageable pageable);
}