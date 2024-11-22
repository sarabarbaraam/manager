package com.sarabarbara.manager.models.games;

import lombok.*;

import java.util.Objects;

/**
 * Metacritic class
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
public class Metacritic {

    /**
     * The score
     */

    private int score;

    /**
     * The url
     */

    private String url;

    /**
     * The equals
     */

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Metacritic that)) return false;
        return getScore() == that.getScore()
                && Objects.equals(getUrl(), that.getUrl());
    }

    /**
     * The hashCode
     */

    @Override
    public int hashCode() {
        return Objects.hash(getScore(), getUrl());
    }

}
