package com.mflix.stream;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/movies/{id}/file")
public class FileController {

    private final FileIoUtils fileIoUtils;

    private final MovieApi movieApi;

    private final ApiUrlBuilder apiUrlBuilder;

    public FileController(FileIoUtils fileIoUtils, MovieApi movieApi, ApiUrlBuilder apiUrlBuilder) {
        this.fileIoUtils = fileIoUtils;
        this.movieApi = movieApi;
        this.apiUrlBuilder = apiUrlBuilder;
    }

    @GetMapping
    public ResponseEntity<?> download(@PathVariable("id") String movieId, HttpServletResponse response) throws IOException {
        Movie result = movieApi.findById(movieId);
        if (result == null) {
            throw new RuntimeException("Resource not found");
        }
        fileIoUtils.stream(result.filePath, response.getOutputStream());
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", fileIoUtils.getPathFileName(result.filePath)))
                .build();
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> upload(@PathVariable("id") String movieId, @RequestParam("file") File movieFile) {
        Movie result = movieApi.findById(movieId);
        if (result == null) {
            throw new RuntimeException("Resource not found");
        }
        String savedFilePath = fileIoUtils.save(movieFile);
        movieApi.update(new Movie(movieId, savedFilePath));
        String uri = String.format("/movies/%s/file", movieId);
        return ResponseEntity
                .created(apiUrlBuilder.build(uri))
                .build();
    }
}
