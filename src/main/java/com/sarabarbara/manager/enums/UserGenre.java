package com.sarabarbara.manager.enums;


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

    M("Male"),
    F("Female"),
    NB("Non Binary"),
    PNTS("Prefer not to say");

    private final String description;
}
