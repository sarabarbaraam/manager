package com.sarabarbara.manager.dto.games;

import lombok.*;

import java.util.Objects;

/**
 * GamesSearchDTO class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 20/11/2024
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GamesSearchDTO {

    /**
     * The name
     */

    private String name;

    /**
     * The capsuleImage
     */

    private String capsuleImage;

    /**
     * The shortDescription
     */

    private String shortDescription;

    /**
     * The equals
     */

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GamesSearchDTO that)) return false;
        return Objects.equals(name, that.name)
                && Objects.equals(capsuleImage, that.capsuleImage)
                && Objects.equals(shortDescription, that.shortDescription);
    }

    /**
     * The hashCode
     */

    @Override
    public int hashCode() {
        return Objects.hash(name, capsuleImage, shortDescription);
    }

}
