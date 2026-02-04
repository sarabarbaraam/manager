package com.sarabarbara.manager.shared;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * GenreEnum class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 30/12/2025
 */

@Getter
@AllArgsConstructor
@Schema(name = "Genre Enum", description = "Enumeration for user genres")
public enum GenreEnum {

    M("Male"),
    F("Female"),
    NB("Non-binary"),
    NP("Not provided");

    private final String description;
}
