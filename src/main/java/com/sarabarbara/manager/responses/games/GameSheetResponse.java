package com.sarabarbara.manager.responses.games;

import com.sarabarbara.manager.dto.games.GamesSheetDTO;
import lombok.*;

import java.util.Objects;

/**
 * GameSheetResponse class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 27/11/2024
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class GameSheetResponse {

    /**
     * The success
     */

    private boolean success;

    /**
     * The gamesSheet
     */

    private GamesSheetDTO gameSheet;

    /**
     * The message
     */

    private String message;

    /**
     * The equals
     *
     * @param o the o
     *
     * @return the equals
     */

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GameSheetResponse that)) return false;
        return isSuccess() == that.isSuccess()
                && Objects.equals(getGameSheet(), that.getGameSheet())
                && Objects.equals(getMessage(), that.getMessage());
    }

    /**
     * The hasCode
     *
     * @return the hashCode
     */

    @Override
    public int hashCode() {
        return Objects.hash(isSuccess(), getGameSheet(), getMessage());
    }

}
