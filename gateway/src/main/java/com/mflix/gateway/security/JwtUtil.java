package com.mflix.gateway.security;

import com.mflix.gateway.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private final static String jwtRawKey = "123456789";

    public String createJwt(User authenticatedUser) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("alg", SignatureAlgorithm.HS256.getValue());
        headers.put("typ", "JWT");
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", authenticatedUser.id);
        claims.put("authorities", authenticatedUser.authorities);
        return Jwts.builder()
                .setHeader(headers)
                .setClaims(claims)
                .signWith(getKey())
                .compact();
    }

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
