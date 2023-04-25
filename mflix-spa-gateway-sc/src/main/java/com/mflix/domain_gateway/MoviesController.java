package com.mflix.domain_gateway;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MoviesController {

    private final MovieCompositionApi movieCompositionApi;

    private final MoviesApi moviesApi;

    public MoviesController(MovieCompositionApi movieCompositionApi, MoviesApi moviesApi) {
        this.movieCompositionApi = movieCompositionApi;
        this.moviesApi = moviesApi;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        return ResponseEntity.ok(movieCompositionApi.findById(id));
    }

    @GetMapping
    public ResponseEntity<?> search() {
        return ResponseEntity.ok(moviesApi.search());
    }
}
