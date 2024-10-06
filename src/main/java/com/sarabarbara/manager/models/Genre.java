package com.sarabarbara.manager.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Genre class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 04/10/2024
 */

@AllArgsConstructor
@Getter
@ToString
public enum Genre {

    M("Male"),
    F("Female"),
    NB("Non Binary"),
    PNTS("Prefer not to say");

    private final String description;
}
