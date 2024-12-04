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

    private GamesSheetDTO gamesSheet;

    /**
     * The equals
     */

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GameSheetResponse that)) return false;
        return isSuccess() == that.isSuccess()
                && Objects.equals(getGamesSheet(), that.getGamesSheet());
    }

    /**
     * The hashCode
     */

    @Override
    public int hashCode() {
        return Objects.hash(isSuccess(), getGamesSheet());
    }

}
