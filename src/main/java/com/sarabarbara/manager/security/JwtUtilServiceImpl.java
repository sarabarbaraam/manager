package com.sarabarbara.manager.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtUtilServiceImpl implements JwtUtilService {

    @Value("${jwt.secret}")
    String SECRET_KEY;

    @Value("${jwt.expiration}")
    long EXPIRATION;

    @Contract(" -> new")
    private @NotNull SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    @Override
    public String generateToken(@NotNull UserDetail userDetails) {

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).toList())
                .issuedAt(new Date())
                .expiration(Date.from(Instant.now().plusMillis(EXPIRATION)))
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    @Override
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    @Override
    public boolean validateToken(String token, @NotNull UserDetail userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    @Override
    public boolean isTokenExpired(String token) {
        Date expiration = extractExpiration(token);
        return expiration.before(new Date());
    }

    @Override
    public Date extractExpiration(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }
}