package com.mflix.auth;

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

    private final UserApi userApi;

    public SuccessLoginHandler(JwtUtil jwtUtil, UserApi userApi) {
        this.jwtUtil = jwtUtil;
        this.userApi = userApi;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        User authenticatedUser = userApi.search(new UserSearchParams(username));
        String jwt = jwtUtil.createJwt(authenticatedUser);
        response.getOutputStream().print(jwt);
    }
}
