package com.mflix.movie_composition;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("comment")
public interface CommentsApi {

    @GetMapping("/comments")
    Object search(CommentsSearchQueryParams commentsSearchQueryParams);
}
