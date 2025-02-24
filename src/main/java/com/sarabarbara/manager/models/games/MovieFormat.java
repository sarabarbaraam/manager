package com.sarabarbara.manager.models.games;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Objects;

/**
 * MovieFormat class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 20/12/2024
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieFormat {

    /**
     * The movie480
     */

    @JsonProperty("480")
    private String movie480;

    /**
     * The moviemax
     */

    @JsonProperty("max")
    private String movieMax;

    /**
     * The equals
     */

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MovieFormat that)) return false;
        return Objects.equals(getMovie480(), that.getMovie480()) && Objects.equals(getMovieMax(), that.getMovieMax());
    }

    /**
     * The hashCode
     */

    @Override
    public int hashCode() {
        return Objects.hash(getMovie480(), getMovieMax());
    }

}
