package com.usuariosplazoleta.microservicio_usuarios.infrastructure.configuration.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    @Value("${security.jwt.secret}") private String secret;
    @Value("${security.jwt.expiration-minutes}") private long minutes;

    public String generate(String subjectEmail, Long userId, Collection<String> roleAuthorities) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + minutes * 60_000);
        return Jwts.builder()
                .setSubject(subjectEmail)            // email
                .claim("uid", userId)               // <-- útil para ownership entre MS
                .claim("roles", roleAuthorities)    // ["ROLE_ADMINISTRADOR",…]
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }

    public Jws<Claims> parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token);
    }
}
