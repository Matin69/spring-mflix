package com.mflix.core.movie;

import com.mflix.core.common.RestExceptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Movie>> search(@RequestParam("text") String searchText) {
        return ResponseEntity.ok(movieRepository.search(searchText));
    }

    @GetMapping
    public ResponseEntity<List<Movie>> query(MovieQueryParams movieQueryParams) {
        return ResponseEntity.ok(movieRepository.query(movieQueryParams));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> findById(@PathVariable String id) {
        Movie foundMovie = movieRepository.findById(id)
                .orElseThrow(() -> RestExceptions.resourceNotFoundException);
        return ResponseEntity.ok(foundMovie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteById(@PathVariable String id) {
        Movie movie = movieRepository
                .findById(id)
                .orElseThrow(() -> RestExceptions.resourceNotFoundException);
        movieRepository.deleteById(id);
        return ResponseEntity.ok(movie);
    }

    @PutMapping
    public ResponseEntity<Movie> save(@RequestBody MovieRequest movieRequest) {
        Movie movie = new Movie(movieRequest.id, movieRequest.plot, movieRequest.genres, movieRequest.title,
                movieRequest.fullPlot, movieRequest.languages, movieRequest.released, movieRequest.directors,
                movieRequest.rated, movieRequest.awards, movieRequest.year, movieRequest.imdb,
                movieRequest.countries, movieRequest.type, movieRequest.tomatoes, movieRequest.filePath);
        Movie savedMovie = movieRepository.save(movie);
        return ResponseEntity.ok(savedMovie);
    }
}
