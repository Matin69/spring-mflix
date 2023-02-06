package com.mflix.filedistributor;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "file-distributor", url = "${mflix.movies.api.url}")
public interface MovieApi {

    @GetMapping("/{id}")
    Movie findById(@PathVariable("id") String movieId);

    @PutMapping
    void update(@RequestBody Movie movie);
}
