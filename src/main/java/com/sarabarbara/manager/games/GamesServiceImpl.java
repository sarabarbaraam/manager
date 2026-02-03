package com.sarabarbara.manager.games;


import com.sarabarbara.manager.games.dtos.GameListDTO;
import com.sarabarbara.manager.games.dtos.GameSearchDTO;
import com.sarabarbara.manager.games.dtos.GameSheetDTO;
import com.sarabarbara.manager.infrastructure.external.steam.SteamClient;
import com.sarabarbara.manager.shared.PagedResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * GamesServiceImpl class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 13/01/2026
 */

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class GamesServiceImpl implements GamesService {

    private final SteamClient steamClient;

    @Override
    public PagedResponse<GameSearchDTO> searchGames(String gameName, int page, int size) {

        log.info("GamesServiceImpl - searchGames called");
        List<GameSearchDTO> gamesList = steamClient.searchGame(gameName);

        log.info(
                "GamesServiceImpl - searchGames retrieved {} games:\n{}",
                gamesList.size(),
                gamesList.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining("\n"))
        );

        List<GameSearchDTO> gamesSearched = gamesList.stream()
                .skip((long) page * size)   // skip to the correct elements
                .limit(size)
                .toList();

        return new PagedResponse<>(
                gamesSearched,
                page,
                size,
                gamesList.size(),
                (int) Math.ceil((double) gamesList.size() / size)
        );
    }

    @Override
    public PagedResponse<GameListDTO> listGames(@NotNull List<GameSearchDTO> searchedGame, int page, int size) {

       /* log.info("GamesServiceImpl - listGames called");
        List<GameListDTO> gameListDTOs = steamClient.getGameDetailsBatch(searchedGame);

        log.info("GamesServiceImpl - listGames retrieved {} games", gameListDTOs.size());
        List<GameListDTO> gameList = gameListDTOs.stream()
                .skip((long) page * size)   // pagination: skip to the correct elements
                .limit(size)
                .toList();

        return new PagedResponse<>(
                gameList,
                page,
                size,
                gameListDTOs.size(),
                (int) Math.ceil((double) gameListDTOs.size() / size)
        );*/
        return null;
    }

    @Override
    public GameSheetDTO gameSheet(Integer gameId) {

       /* log.info("GamesServiceImpl - gameSheet called for gameId: {}", gameId);
        log.info("GamesServiceImpl - gameSheet retrieved details for gameId: {}", gameId);
        return steamClient.getGameSheetInformation(gameId);*/
        return null;
    }
}