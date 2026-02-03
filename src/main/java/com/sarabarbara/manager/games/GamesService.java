package com.sarabarbara.manager.games;


import com.sarabarbara.manager.games.dtos.GameListDTO;
import com.sarabarbara.manager.games.dtos.GameSearchDTO;
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

    PagedResponse<GameSearchDTO> searchGames(String gameName, int page, int size);
    PagedResponse<GameListDTO> listGames(List<GameSearchDTO> searchedGame, int page, int size);
    GameSheetDTO gameSheet(Integer gameId);
}
