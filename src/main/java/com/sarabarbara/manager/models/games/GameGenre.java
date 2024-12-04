package com.sarabarbara.manager.models.games;

import lombok.*;

import java.util.Objects;

/**
 * GameGenre class
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
public class GameGenre {

    /**
     * The id
     */

    private String id;

    /**
     * The description
     */

    private String description;

    /**
     * The equals
     */

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GameGenre gameGenre)) return false;
        return Objects.equals(getId(), gameGenre.getId())
                && Objects.equals(getDescription(), gameGenre.getDescription());
    }

    /**
     * The hashCode
     */

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDescription());
    }

}
