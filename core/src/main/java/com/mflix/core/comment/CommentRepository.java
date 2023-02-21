package com.mflix.core.comment;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment, String>, CommentSearchRepository {
}