package com.mflix.stream;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "stream", url = "${mflix.apis.core.url}")
public interface MovieApi {

    @GetMapping("/movies/{id}")
    Movie findById(@PathVariable("id") String movieId);

    @PutMapping("/movies")
    void update(@RequestBody Movie movie);
}
