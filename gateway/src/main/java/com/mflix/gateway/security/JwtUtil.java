package com.mflix.gateway.security;

import com.mflix.gateway.user.Role;
import com.mflix.gateway.user.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

@Component
public class JwtUtil {

    private final static String jwtRawKey = "saklnnckancdAWDNCa@QWEWDFSFVSFSDFSADSXDVFSDXVBFBDSXFBDFBBGJHZ235465YHTNJHFGqnw9283987783123812938132";

    public String createJwt(User user) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("alg", SignatureAlgorithm.HS256.getValue());
        headers.put("typ", "JWT");
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", user.id);
        claims.put("authorities", getAuthorities(user));
        return Jwts.builder()
                .setHeader(headers)
                .setClaims(claims)
                .signWith(getKey())
                .compact();
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(jwtRawKey.getBytes(StandardCharsets.UTF_8));
    }

    private Collection<String> getAuthorities(User loadedUser) {
        List<String> authorities = new ArrayList<>();
        for (Role role : loadedUser.roles) {
            authorities.addAll(role.permissions);
        }
        return authorities;
    }
}
