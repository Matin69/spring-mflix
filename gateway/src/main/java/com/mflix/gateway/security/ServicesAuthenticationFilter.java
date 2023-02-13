package com.mflix.gateway.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;

public class ServicesAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    protected ServicesAuthenticationFilter(AuthenticationManager authenticationManager) {
        super("/**", authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            String jwt = authorizationHeader.split(" ")[1];
            JwtAuthentication jwtAuthentication = new JwtAuthentication(jwt);
            return getAuthenticationManager().authenticate(jwtAuthentication);
        }
        throw new AuthenticationException("") {
            @Override
            public String getMessage() {
                return super.getMessage();
            }
        };
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        if (request.getRequestURI().equals("/error")) {
            return false;
        }
        return super.requiresAuthentication(request, response);
    }
}
