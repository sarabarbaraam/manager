package com.sarabarbara.manager.users;


import lombok.RequiredArgsConstructor;

/**
 * RolesEnum class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 06/01/2026
 */

@RequiredArgsConstructor
public enum RolesEnum {

    USER("USER"),
    PREMIUM("PREMIUM");

    private final String description;
}
