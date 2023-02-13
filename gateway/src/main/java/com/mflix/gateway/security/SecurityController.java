package com.mflix.gateway.security;

import com.mflix.gateway.user.User;
import com.mflix.gateway.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Date;

@RestController
public class SecurityController {

    private final DaoAuthenticationProvider daoAuthenticationProvider;

    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public SecurityController(DaoAuthenticationProvider daoAuthenticationProvider, JwtUtil jwtUtil, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.daoAuthenticationProvider = daoAuthenticationProvider;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = createAuthenticationFromCredentials(loginRequest);
        authenticate(authenticationToken);
        User authenticatedUser = fetchAuthenticatedUser(authenticationToken);
        String jwt = createJwt(authenticatedUser);
        return ResponseEntity.ok(jwt);
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
        String jwt = createJwt(user);
        return ResponseEntity.ok(jwt);
    }

    private UsernamePasswordAuthenticationToken createAuthenticationFromCredentials(LoginRequest loginRequest) {
        return new UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password, null);
    }

    private void authenticate(UsernamePasswordAuthenticationToken authenticationToken) {
        daoAuthenticationProvider.authenticate(authenticationToken);
    }

    private User fetchAuthenticatedUser(UsernamePasswordAuthenticationToken authenticationToken) {
        return userRepository.findByName(authenticationToken.getName()).orElse(null);
    }

    private String createJwt(User authenticatedUser) {
        return jwtUtil.createJwt(authenticatedUser);
    }
}
