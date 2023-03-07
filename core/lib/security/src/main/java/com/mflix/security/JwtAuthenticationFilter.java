package com.mflix.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String[][] IGNORE_AUTHENTICATION_PATHS = {
            {"/error", "GET"},
            {"/signup", "POST"},
            {"/login", "POST"},
            {"/", "GET"}
    };

    private final JwtParser jwtParser;

    public JwtAuthenticationFilter(JwtParser jwtParser) {
        this.jwtParser = jwtParser;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (requiresAuthentication(request)) {
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader != null) {
                String jwt = authorizationHeader.split(" ")[1];
                try {
                    jwtParser.verifyJwt(jwt);
                    String subject = (String) jwtParser.getClaim(jwt, "sub");
                    List<String> authorities = (List<String>) jwtParser.getClaim(jwt, "authorities");
                    SecurityContext context = SecurityContextHolder.createEmptyContext();
                    context.setAuthentication(new UsernamePasswordAuthenticationToken(
                            subject,
                            null,
                            getAuthorities(authorities)
                    ));
                    SecurityContextHolder.setContext(context);
                    filterChain.doFilter(request, response);
                    return;
                } catch (JwtException e) {
                    e.printStackTrace();
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean requiresAuthentication(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        String requestMethod = request.getMethod().toUpperCase();
        long count = Arrays.stream(IGNORE_AUTHENTICATION_PATHS)
                .filter(ignoreAuthenticationPath -> requestUri.equals(ignoreAuthenticationPath[0]) && requestMethod.equals(ignoreAuthenticationPath[1]))
                .count();
        return count == 0;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
