package com.mflix.auth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "user", url = "${mflix.apis.user.url}")
public interface UserApi {

    @GetMapping("/users")
    User search(UserSearchParams userSearchParams);

    @PostMapping("/users")
    User save(UserRequest userRequest);
}
