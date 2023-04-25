package com.mflix.movie.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("comment")
public interface CommentsApi {

    @GetMapping("/comments")
    Object search(CommentsQueryParams commentsQueryParams);
}
