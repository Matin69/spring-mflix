package com.mflix.movie_composition;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/composition/movies")
public class MovieCompositionController {

    private final MoviesApi moviesApi;

    private final CommentsApi commentsApi;

    public MovieCompositionController(MoviesApi moviesApi, CommentsApi commentsApi) {
        this.moviesApi = moviesApi;
        this.commentsApi = commentsApi;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> findById(@PathVariable String id) {
        CommentsSearchQueryParams commentsSearchQueryParams = new CommentsSearchQueryParams();
        commentsSearchQueryParams.movieId = id;
        Object movie = moviesApi.findById(id);
        Object comments = commentsApi.search(commentsSearchQueryParams);
        MovieResponse movieResponse = new MovieResponse(movie, comments);
        return ResponseEntity.ok(movieResponse);
    }
}
