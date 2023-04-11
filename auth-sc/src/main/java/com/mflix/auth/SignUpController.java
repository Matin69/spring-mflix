package com.mflix.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class SignUpController {

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    private final RoleApi roleApi;

    private final UserApi userApi;

    public SignUpController(JwtUtil jwtUtil, PasswordEncoder passwordEncoder, RoleApi roleApi, UserApi userApi) {
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.roleApi = roleApi;
        this.userApi = userApi;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> saveUser(@RequestBody SignUpRequest signUpRequest) {
        UserRequest userRequest = new UserRequest();
        userRequest.name = signUpRequest.name;
        userRequest.email = signUpRequest.email;
        userRequest.password = passwordEncoder.encode(signUpRequest.password);
        userRequest.roles = Collections.singletonList(roleApi.search(new RoleSearchParams("USER")));
        User savedUser = userApi.save(userRequest);
        savedUser.roles = userRequest.roles;
        String jwt = jwtUtil.createJwt(savedUser);
        return ResponseEntity.ok(jwt);
    }
}
