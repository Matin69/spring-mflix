package com.mflix.filedistributor;

import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/movies/{id}/file")
public class FileController {

    private final FileSaver fileSaver;

    private final MovieApi movieApi;

    public FileController(FileSaver fileSaver, MovieApi movieApi) {
        this.fileSaver = fileSaver;
        this.movieApi = movieApi;
    }

    @GetMapping
    public void download(@PathVariable("id") String movieId) {
        validateMovieId(movieId);
    }

    @PostMapping(path = "", consumes = "multipart/form-data")
    public void upload(@PathVariable("id") String movieId, @RequestParam("file") File movieFile) {
        validateMovieId(movieId);
        String savedFilePath = fileSaver.save(movieFile);
        movieApi.update(movieId, new Movie(savedFilePath));
    }

    private void validateMovieId(String movieId) {
        Movie result = movieApi.findById(movieId);
        if (result == null) {
            throw new RuntimeException("Resource not found");
        }
    }
}
