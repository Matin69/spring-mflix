package com.mflix.app.comment;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Component
public class CommentSearchRepositoryImpl implements CommentSearchRepository {

    private final MongoTemplate mongoTemplate;

    public CommentSearchRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Comment> search(CommentQueryParams commentQueryParams) {
        Criteria criteria = new Criteria();
        if (commentQueryParams.getEmail() != null) {
            criteria = where("email").is(commentQueryParams.getEmail());
        }
        if (commentQueryParams.getMovieId() != null) {
            criteria.and("movie_id").is(new ObjectId(commentQueryParams.getMovieId()));
        }
        Query query = new Query(criteria);
        query.with(commentQueryParams.getPagination());
        return mongoTemplate.query(Comment.class)
                .matching(query)
                .all();
    }
}
