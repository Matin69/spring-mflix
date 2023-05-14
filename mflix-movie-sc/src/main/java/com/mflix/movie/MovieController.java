package com.mflix.movie;

import com.mflix.core.common.RestExceptions;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/movies")
@Transactional
public class MovieController {

    private final MovieRepository movieRepository;

    private final MovieFileService movieFileService;

    public MovieController(MovieRepository movieRepository, MovieFileService movieFileService) {
        this.movieRepository = movieRepository;
        this.movieFileService = movieFileService;
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Movie> create(@RequestParam(name = "movieCreationRequest") MoviePostRequest moviePostRequest,
                                        @RequestParam(name = "movieFile") MultipartFile movieFilePart) {
        String savedFilePath = movieFileService.save(movieFilePart);
        Movie movie = new Movie(moviePostRequest.plot, moviePostRequest.genres, moviePostRequest.title,
                moviePostRequest.fullPlot, moviePostRequest.languages, moviePostRequest.released, moviePostRequest.directors,
                moviePostRequest.rated, moviePostRequest.awards, moviePostRequest.year, moviePostRequest.imdb,
                moviePostRequest.countries, moviePostRequest.type, moviePostRequest.tomatoes, savedFilePath);
        Movie savedMovie = movieRepository.save(movie);
        return ResponseEntity.ok(savedMovie);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Movie> update(@PathVariable String id,
                                        @RequestParam(name = "movieCreationRequest") MoviePostRequest moviePostRequest,
                                        @RequestParam(name = "movieFile") MultipartFile movieFilePart) {
        String savedFilePath = movieFileService.save(movieFilePart);
        Movie movie = new Movie(id, moviePostRequest.plot, moviePostRequest.genres, moviePostRequest.title,
                moviePostRequest.fullPlot, moviePostRequest.languages, moviePostRequest.released, moviePostRequest.directors,
                moviePostRequest.rated, moviePostRequest.awards, moviePostRequest.year, moviePostRequest.imdb,
                moviePostRequest.countries, moviePostRequest.type, moviePostRequest.tomatoes, savedFilePath);
        Movie savedMovie = movieRepository.save(movie);
        return ResponseEntity.ok(savedMovie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteById(@PathVariable String id) {
        Movie movie = movieRepository
                .findById(id)
                .orElseThrow(() -> RestExceptions.resourceNotFoundException);
        movieRepository.deleteById(id);
        return ResponseEntity.ok(movie);
    }
}
