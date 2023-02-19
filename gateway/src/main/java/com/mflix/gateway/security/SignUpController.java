package com.mflix.gateway.security;

import com.mflix.gateway.user.User;
import com.mflix.gateway.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Date;

@RestController
public class SignUpController {

    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public SignUpController(JwtUtil jwtUtil, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> saveUser(@RequestBody SignUpRequest signUpRequest) {
        User user = new User();
        user.name = signUpRequest.name;
        user.email = signUpRequest.email;
        user.password = passwordEncoder.encode(signUpRequest.password);
        user.creationDate = new Date();
        user.authorities = Collections.singleton("ROLE_USER");
        userRepository.save(user);
        String jwt = jwtUtil.createJwt(user);
        return ResponseEntity.ok(jwt);
    }
}