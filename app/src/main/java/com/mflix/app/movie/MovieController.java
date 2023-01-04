package com.mflix.app.movie;

import com.mflix.app.common.RestCrudController;
import com.mflix.app.common.RestExceptions;
import com.mflix.app.common.RestResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies")
public class MovieController implements RestCrudController<Movie, MovieRequest, String> {

    private final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    @GetMapping
    public ResponseEntity<RestResponse<List<Movie>>> search() {
        List<Movie> foundMovies = movieRepository.findAll(Pageable.ofSize(10))
                .stream()
                .collect(Collectors.toList());
        RestResponse<List<Movie>> response = new RestResponse<>(true, "search", foundMovies);
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<Movie>> findById(@PathVariable String id) {
        Movie foundMovie = movieRepository.findById(id)
                .orElseThrow(() -> RestExceptions.resourceNotFoundException);
        RestResponse<Movie> response = new RestResponse<>(true, "get", foundMovie);
        return ResponseEntity.ok(response);
    }

    @Override
    @PostMapping
    public ResponseEntity<RestResponse<Movie>> save(@RequestBody MovieRequest movieRequest) {
        Movie movie = new Movie(movieRequest.id, movieRequest.plot, movieRequest.genres, movieRequest.title,
                movieRequest.fullPlot, movieRequest.languages, movieRequest.released, movieRequest.directors,
                movieRequest.rated, movieRequest.awards, movieRequest.year, movieRequest.imdb,
                movieRequest.countries, movieRequest.type, movieRequest.tomatoes);
        Movie savedMovie = movieRepository.save(movie);
        RestResponse<Movie> response = new RestResponse<>(true, "save", savedMovie);
        return ResponseEntity.ok(response);
    }
}
