package com.mflix.app.movie;

import com.mflix.app.common.RestCrudController;
import com.mflix.app.common.RestExceptions;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies")
public class MovieController implements RestCrudController {

    private final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    @GetMapping
    public ResponseEntity<List<Movie>> search() {
        List<Movie> foundMovies = movieRepository.findAll(Pageable.ofSize(10))
                .stream()
                .collect(Collectors.toList());
        return ResponseEntity.ok(foundMovies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> findById(@PathVariable String id) {
        Movie foundMovie = movieRepository.findById(id)
                .orElseThrow(() -> RestExceptions.resourceNotFoundException);
        return ResponseEntity.ok(foundMovie);
    }

    @PostMapping
    public ResponseEntity<Movie> save(@RequestBody MovieRequest movieRequest) {
        Movie movie = new Movie(movieRequest.id, movieRequest.plot, movieRequest.genres, movieRequest.title,
                movieRequest.fullPlot, movieRequest.languages, movieRequest.released, movieRequest.directors,
                movieRequest.rated, movieRequest.awards, movieRequest.year, movieRequest.imdb,
                movieRequest.countries, movieRequest.type, movieRequest.tomatoes);
        Movie savedMovie = movieRepository.save(movie);
        return ResponseEntity.ok(savedMovie);
    }
}
