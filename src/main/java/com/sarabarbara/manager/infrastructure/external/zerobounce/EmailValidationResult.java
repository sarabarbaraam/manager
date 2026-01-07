package com.sarabarbara.manager.infrastructure.external.zerobounce;


import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * EmailValidationResult class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 07/01/2026
 */

@Slf4j
public record EmailValidationResult(boolean valid, boolean verified, String reason) {

    @Contract(" -> new")
    public static @NotNull EmailValidationResult success() {
        return new EmailValidationResult(true, true, null);
    }

    @Contract("_ -> new")
    public static @NotNull EmailValidationResult invalid(String reason) {
        return new EmailValidationResult(false, true, reason);
    }

    @Contract(" -> new")
    public static @NotNull EmailValidationResult unverified() {
        return new EmailValidationResult(true, false, "fallback");
    }
}
