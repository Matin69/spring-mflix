package com.mflix.stream;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/movies/{id}/file")
public class FileController {

    private final FileIoUtils fileIoUtils;

    private final MovieApi movieApi;

    private final MflixProperties mflixProperties;

    public FileController(FileIoUtils fileIoUtils, MovieApi movieApi, MflixProperties mflixProperties) {
        this.fileIoUtils = fileIoUtils;
        this.movieApi = movieApi;
        this.mflixProperties = mflixProperties;
    }

    @GetMapping
    public ResponseEntity<?> download(@PathVariable("id") String movieId, HttpServletResponse response) throws IOException {
        Movie result = movieApi.findById(movieId);
        if (result == null) {
            throw new RuntimeException("Resource not found");
        }
        if (result.filePath == null) {
            throw new RuntimeException("Could not find movie file");
        }
        fileIoUtils.stream(result.filePath, response.getOutputStream());
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", fileIoUtils.getPathFileName(result.filePath)))
                .build();
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> upload(@PathVariable("id") String movieId, @RequestParam("movie_file") MultipartFile uploadedFilePart) {
        Movie result = movieApi.findById(movieId);
        if (result == null) {
            throw new RuntimeException("Resource not found");
        }
        String savedFilePath = fileIoUtils.save(uploadedFilePart);
        movieApi.update(new Movie(movieId, savedFilePath));
        return ResponseEntity
                .created(buildUploadedFileURI(movieId))
                .build();
    }

    public URI buildUploadedFileURI(String movieId) {
        String uploadedFileHttpPath = String.format("/movies/%s/file", movieId);
        return UriComponentsBuilder.fromHttpUrl(mflixProperties.getStreamUrl())
                .path(uploadedFileHttpPath)
                .build()
                .toUri();
    }
}
