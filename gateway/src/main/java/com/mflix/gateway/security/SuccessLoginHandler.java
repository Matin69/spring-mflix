package com.mflix.gateway.security;

import com.mflix.gateway.user.User;
import com.mflix.gateway.user.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SuccessLoginHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    public SuccessLoginHandler(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        User authenticatedUser = userRepository.findByName(username).orElseThrow();
        String jwt = jwtUtil.createJwt(authenticatedUser);
        response.getOutputStream().print(jwt);
    }
}
