package com.sarabarbara.manager.enums.user;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * UserGenre class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 04/10/2024
 */

@AllArgsConstructor
@Getter
@ToString
public enum UserGenre {

    /**
     * The Enum
     */

    M("Male"),
    F("Female"),
    NB("Non Binary"),
    PNTS("Prefer not to say");

    /**
     * The description
     */

    private final String description;
}
