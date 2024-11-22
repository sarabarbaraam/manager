package com.sarabarbara.manager.models.games;

import lombok.*;

import java.util.Objects;

/**
 * Categories class
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
public class Categories {

    /**
     * The id
     */

    private int id;

    /**
     * The description
     */

    private String description;

    /**
     * The equals
     */

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Categories that)) return false;
        return getId() == that.getId()
                && Objects.equals(getDescription(), that.getDescription());
    }

    /**
     * The hashCode
     */

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDescription());
    }

}
