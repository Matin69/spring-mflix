package com.mflix.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
public class JwtParser {

    private final static String jwtRawKey = "saklnnckancdAWDNCa@QWEWDFSFVSFSDFSADSXDVFSDXVBFBDSXFBDFBBGJHZ235465YHTNJHFGqnw9283987783123812938132";

    public void verifyJwt(String jwt) {
        Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(jwt);
    }

    public Object getClaim(String jwt, String name) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        return claims.get(name);
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(jwtRawKey.getBytes(StandardCharsets.UTF_8));
    }
}
