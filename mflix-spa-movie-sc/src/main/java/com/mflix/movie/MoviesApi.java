package com.mflix.movie;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("movie")
public interface MoviesApi {

    @GetMapping("/movies/{id}")
    Object findById(@PathVariable String id);
}
