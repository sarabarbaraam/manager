package com.sarabarbara.manager.security;


/**
 * AuthService class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 05/01/2026
 */

public interface AuthService {

    Long getCurrentUserId();
    AuthResponse login(LoginRequest request);
}
