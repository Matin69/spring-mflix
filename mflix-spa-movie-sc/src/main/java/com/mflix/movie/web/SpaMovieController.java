package com.mflix.movie.web;

import com.mflix.movie.api.CommentsApi;
import com.mflix.movie.api.CommentsQueryParams;
import com.mflix.movie.api.MoviesApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class SpaMovieController {

    private final MoviesApi moviesApi;

    private final CommentsApi commentsApi;

    public SpaMovieController(MoviesApi moviesApi, CommentsApi commentsApi) {
        this.moviesApi = moviesApi;
        this.commentsApi = commentsApi;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpaMovieResponse> findById(@PathVariable String id) {
        CommentsQueryParams commentsQueryParams = new CommentsQueryParams();
        commentsQueryParams.movieId = id;
        Object movie = moviesApi.findById(id);
        Object comments = commentsApi.search(commentsQueryParams);
        SpaMovieResponse spaMovieResponse = new SpaMovieResponse(movie, comments);
        return ResponseEntity.ok(spaMovieResponse);
    }
}
