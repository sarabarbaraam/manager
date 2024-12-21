package com.sarabarbara.manager.controllers;

import com.sarabarbara.manager.dto.games.GamesSearchDTO;
import com.sarabarbara.manager.dto.games.GamesSheetDTO;
import com.sarabarbara.manager.models.games.Games;
import com.sarabarbara.manager.responses.SearchResponse;
import com.sarabarbara.manager.responses.games.GameSheetResponse;
import com.sarabarbara.manager.services.GamesService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sarabarbara.manager.mappers.GamesMapper.toGamesSearchDTOMapper;
import static com.sarabarbara.manager.mappers.GamesMapper.toGamesSheetDTOMapper;

/**
 * GamesController class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 20/11/2024
 */

@RestController
@AllArgsConstructor
@RequestMapping("/games")
public class GamesController {

    private static final Logger logger = LoggerFactory.getLogger(GamesController.class);

    private final GamesService gamesService;

    @PostMapping("/search")
    public ResponseEntity<SearchResponse<GamesSearchDTO>> searchGame(@RequestBody String gameName,
                                                                     @RequestParam(defaultValue = "1") int page,
                                                                     @RequestParam(defaultValue = "10") int size) {

        try {

            logger.info("Searching game started");

            List<Games> searchedGame = gamesService.searchGames(gameName, page - 1, size);

            if (searchedGame.isEmpty()) {

                logger.info("No games found for name: {}", gameName);

                logger.info("Searching game finished without content");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            List<GamesSearchDTO> gamesSearchDTOS = toGamesSearchDTOMapper(searchedGame);

            int totalPages = (int) Math.ceil((double) searchedGame.size() / size);

            SearchResponse<GamesSearchDTO> response = new SearchResponse<>(
                    gamesSearchDTOS, gamesSearchDTOS.size(), page, totalPages
            );

            logger.info("Total games found with name {}: {}. Current Page: {}. Total Page: {}", gameName,
                    response.getTotalResults(), response.getCurrentPage(), response.getTotalPage());

            logger.info("Games found:");
            gamesSearchDTOS.forEach(games -> logger.info(" - {}", games.getName()));

            logger.info("Searching game finished");
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {

            logger.error("Can't search game: Some internal error ocurred.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new SearchResponse<>(null, 0, 0, 0));
        }
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<GameSheetResponse> gameSheet(@PathVariable Integer gameId) {

        try {

            logger.info("GameSheet started");

            Games game = gamesService.gameSheet(gameId);

            GamesSheetDTO gamesSheetDTO = toGamesSheetDTOMapper(game);

            logger.info("Game sheet information: {}", gamesSheetDTO);
            logger.info("GameSheet finished");
            return ResponseEntity.status(HttpStatus.OK).body(new GameSheetResponse(true, gamesSheetDTO));

        } catch (Exception e) {

            logger.error("Can't load the game's sheet");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GameSheetResponse(false, null));

        }
    }

}
