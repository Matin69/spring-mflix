package com.mflix.admin_gateway.web;

import com.mflix.admin_gateway.api.Movie;
import com.mflix.admin_gateway.api.MovieApi;
import com.mflix.admin_gateway.api.MoviePostRequest;
import com.mflix.admin_gateway.api.MovieQueryParams;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class AdminMovieController {

    private final MovieApi movieApi;

    public AdminMovieController(MovieApi movieApi) {
        this.movieApi = movieApi;
    }

    @GetMapping
    public AdminMovieQueryResponse search(AdminMovieQueryParams adminMovieQueryParams) {
        MovieQueryParams movieQueryParams = new MovieQueryParams();
        movieQueryParams.setTitle(adminMovieQueryParams.getTitle());
        List<Movie> queriedMovies = movieApi.search(movieQueryParams);
        return queriedMovies.stream()
                .map(movie -> new AdminMovieQueryResponse(movie.plot, movie.genres, movie.title, movie.fullPlot, movie.languages, movie.released, movie.directors, movie.rated, movie.awards, movie.year, movie.imdb, movie.countries, movie.type, movie.tomatoes))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Couldn't found any movie"));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AdminMovieUpdateResponse create(@RequestParam("movieCreationRequest") AdminMoviePostRequest adminMoviePostRequest,
                                           @RequestParam("movieFile") MultipartFile movieFilePart) {
        MoviePostRequest moviePostRequest = new MoviePostRequest(adminMoviePostRequest.plot, adminMoviePostRequest.genres, adminMoviePostRequest.title, adminMoviePostRequest.fullPlot, adminMoviePostRequest.languages, adminMoviePostRequest.released, adminMoviePostRequest.directors, adminMoviePostRequest.rated, adminMoviePostRequest.awards, adminMoviePostRequest.year, adminMoviePostRequest.imdb, adminMoviePostRequest.countries, adminMoviePostRequest.type, adminMoviePostRequest.tomatoes);
        Movie createdMovie = movieApi.create(moviePostRequest, movieFilePart);
        return Optional.of(createdMovie)
                .map(movie -> new AdminMovieUpdateResponse(createdMovie.plot, createdMovie.genres, createdMovie.title, createdMovie.fullPlot, createdMovie.languages, createdMovie.released, createdMovie.directors, createdMovie.rated, createdMovie.awards, createdMovie.year, createdMovie.imdb, createdMovie.countries, createdMovie.type, createdMovie.tomatoes))
                .orElseThrow(() -> new RuntimeException("Couldn't create movie"));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AdminMovieUpdateResponse update(@PathVariable String id,
                                           @RequestParam("movieCreationRequest") AdminMoviePostRequest adminMoviePostRequest,
                                           @RequestParam("movieFile") MultipartFile movieFilePart) {
        MoviePostRequest moviePostRequest = new MoviePostRequest(adminMoviePostRequest.plot, adminMoviePostRequest.genres, adminMoviePostRequest.title, adminMoviePostRequest.fullPlot, adminMoviePostRequest.languages, adminMoviePostRequest.released, adminMoviePostRequest.directors, adminMoviePostRequest.rated, adminMoviePostRequest.awards, adminMoviePostRequest.year, adminMoviePostRequest.imdb, adminMoviePostRequest.countries, adminMoviePostRequest.type, adminMoviePostRequest.tomatoes);
        Movie updatedMovie = movieApi.update(id, moviePostRequest, movieFilePart);
        return Optional.of(updatedMovie)
                .map(movie -> new AdminMovieUpdateResponse(updatedMovie.plot, updatedMovie.genres, updatedMovie.title, updatedMovie.fullPlot, updatedMovie.languages, updatedMovie.released, updatedMovie.directors, updatedMovie.rated, updatedMovie.awards, updatedMovie.year, updatedMovie.imdb, updatedMovie.countries, updatedMovie.type, updatedMovie.tomatoes))
                .orElseThrow(() -> new RuntimeException("Couldn't update movie"));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        movieApi.deleteById(id);
    }
}
