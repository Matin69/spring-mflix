package com.mflix.app.movie;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Component
public class MovieSearchRepositoryImpl implements MovieSearchRepository {

    private final MongoTemplate mongoTemplate;

    public MovieSearchRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Movie> search(MovieQueryParams movieQueryParams) {
        Criteria criteria = new Criteria();
        if (movieQueryParams.getYear() != null) {
            criteria = where("year").is(movieQueryParams.getYear());
        }
        if (movieQueryParams.getTitle() != null) {
            criteria.and("title").is(movieQueryParams.getTitle());
        }
        Query query = new Query(criteria);
        query.with(movieQueryParams.getPagination());
        return mongoTemplate.query(Movie.class)
                .matching(query)
                .all();
    }
}
