package com.mflix.domain_gateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("movies")
public interface MoviesApi {

    @GetMapping("/movies")
    Object search();
}
