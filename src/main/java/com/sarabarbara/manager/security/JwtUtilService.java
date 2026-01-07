package com.sarabarbara.manager.security;


/**
 * JwtUtilService class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 06/01/2026
 */

public interface JwtUtilService {

    String generateToken(UserDetail userDetails);
    String extractUsername(String token);
    boolean validateToken(String token, UserDetail userDetails);
    boolean isTokenExpired(String token);
    java.util.Date extractExpiration(String token);
}
