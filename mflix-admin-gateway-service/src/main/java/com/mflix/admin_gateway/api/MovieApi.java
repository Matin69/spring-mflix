package com.mflix.admin_gateway.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient("movie")
public interface MovieApi {

    @GetMapping
    List<Movie> search(MovieQueryParams movieQueryParams);

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Movie create(@RequestParam("movieCreationRequest") MoviePostRequest moviePostRequest,
                 @RequestParam("movieFile") MultipartFile movieFilePart);

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Movie update(@PathVariable String id,
                 @RequestParam("movieCreationRequest") MoviePostRequest moviePostRequest,
                 @RequestParam("movieFile") MultipartFile movieFilePart);

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable String id);
}
