package com.mflix.gateway.security;

import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ServicesAuthenticationProvider implements AuthenticationProvider {

    private final JwtUtil jwtUtil;

    public ServicesAuthenticationProvider(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String jwt = ((JwtAuthentication) authentication).jwt();
        try {
            jwtUtil.verifyJwt(jwt);
            Long subject = (Long) jwtUtil.getClaim(jwt, "sub");
            List<String> authorities = (List<String>) jwtUtil.getClaim(jwt, "authorities");
            return new UsernamePasswordAuthenticationToken(
                    subject,
                    null,
                    getAuthorities(authorities)
            );
        } catch (JwtException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authenticationClass) {
        return authenticationClass == JwtAuthentication.class;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
