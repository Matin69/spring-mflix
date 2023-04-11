package com.mflix.auth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "role", url = "${mflix.apis.user.url}")
public interface RoleApi {

    @GetMapping("/roles")
    Role search(RoleSearchParams roleSearchParams);
}
