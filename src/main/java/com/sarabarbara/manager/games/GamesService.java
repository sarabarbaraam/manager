package com.sarabarbara.manager.games;


import com.sarabarbara.manager.games.dtos.GameListDTO;
import com.sarabarbara.manager.games.dtos.GameAutocompleteDTO;
import com.sarabarbara.manager.games.dtos.GameSheetDTO;
import com.sarabarbara.manager.shared.PagedResponse;

import java.util.List;

/**
 * GamesService class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 13/01/2026
 */

public interface GamesService {

    List<GameAutocompleteDTO> autocomplete(String query);
    PagedResponse<GameListDTO> listGames(List<GameAutocompleteDTO> searchedGame, int page, int size);
    PagedResponse<GameListDTO> getGames(String gameName, int page, int size);
    GameSheetDTO gameSheet(Integer gameId);
}
