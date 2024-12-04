package com.sarabarbara.manager.models.games;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Objects;

/**
 * Screenshots class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 27/11/2024
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Screenshots {

    /**
     * The pathThumbnail
     */

    @JsonProperty("path_thumbnail")
    private String pathThumbnail;

    /**
     * The pathFull
     */

    @JsonProperty("path_full")
    private String pathFull;

    /**
     * The equals
     */

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Screenshots that)) return false;
        return Objects.equals(getPathThumbnail(), that.getPathThumbnail())
                && Objects.equals(getPathFull(), that.getPathFull());
    }

    /**
     * The hashCode
     */

    @Override
    public int hashCode() {
        return Objects.hash(getPathThumbnail(), getPathFull());
    }

}
