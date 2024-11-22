package com.sarabarbara.manager.services;

import com.sarabarbara.manager.apis.SteamAPI;
import com.sarabarbara.manager.models.games.Games;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.sarabarbara.manager.utils.GamesUtils.paginate;

/**
 * GamesService class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 20/11/2024
 */

@Service
@AllArgsConstructor
public class GamesService {

    private static final Logger logger = LoggerFactory.getLogger(GamesService.class);

    private SteamAPI steamAPI;

    public List<Games> searchGames(String gameName, int page, int size) {

        logger.info("Searching game: {}. Page: {}. Size: {}", gameName, page, size);

        Map<Integer, String> gamesList = steamAPI.getGamesList(gameName);
        List<Games> gameDetail = steamAPI.getGame(gamesList);

        Page<Games> searchedGame = paginate(gameDetail, page, size);

        logger.info("Games found: {}. {}. ", gameDetail.size(), searchedGame);
        return searchedGame.getContent();
    }

}
