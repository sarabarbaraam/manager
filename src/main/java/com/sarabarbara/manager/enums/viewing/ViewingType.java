package com.sarabarbara.manager.enums.viewing;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * ViewingType class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 20/01/2025
 */

@AllArgsConstructor
@Getter
@ToString
public enum ViewingType {

    /**
     * The Enum
     */

    MOVIE("Movie"),
    TV("TV Serie");

    /**
     * The description
     */

    private final String description;
}
