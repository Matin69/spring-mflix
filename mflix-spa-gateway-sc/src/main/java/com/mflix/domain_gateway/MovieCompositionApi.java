package com.mflix.domain_gateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("movieComposition")
public interface MovieCompositionApi {

    @GetMapping("/composition/movies/{id}")
    Object findById(@PathVariable String id);
}
