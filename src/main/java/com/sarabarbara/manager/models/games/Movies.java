package com.sarabarbara.manager.models.games;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

import java.util.Objects;

/**
 * Movies class
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movies {

    /**
     * The id
     */

    private int id;

    /**
     * The name
     */

    private String name;

    /**
     * The thumbnail
     */

    private String thumbnail;

    /**
     * The web
     */

    private JsonNode webm;

    /**
     * The equals
     */

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Movies movies)) return false;
        return getId() == movies.getId()
                && Objects.equals(getName(), movies.getName())
                && Objects.equals(getThumbnail(), movies.getThumbnail())
                && Objects.equals(getWebm(), movies.getWebm());
    }

    /**
     * The hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getThumbnail(), getWebm());
    }

}
