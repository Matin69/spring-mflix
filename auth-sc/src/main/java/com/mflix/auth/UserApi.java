package com.mflix.auth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("user")
public interface UserApi {

    @GetMapping("/users")
    User search(@RequestParam("username") String username);

    @PostMapping("/users")
    User save(UserRequest userRequest);

    @GetMapping("/roles")
    Role searchRoles(RoleSearchParams roleSearchParams);
}
