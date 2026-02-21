package com.sarabarbara.manager.games;


import com.sarabarbara.manager.games.dtos.GameAutocompleteDTO;
import com.sarabarbara.manager.games.dtos.GameListDTO;
import com.sarabarbara.manager.games.dtos.GameSheetDTO;
import com.sarabarbara.manager.shared.BaseResponse;
import com.sarabarbara.manager.shared.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sarabarbara.manager.shared.constants.APIConstants.*;
import static com.sarabarbara.manager.shared.constants.SwaggerGamesExamplesConstants.*;

/**
 * GamesController class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 13/01/2026
 */

@Slf4j
@AllArgsConstructor
@RestController
@Tag(name = "Games", description = "Games related operations")
@RequestMapping("/api/v1/games")
public class GamesController {

    private final GamesService gamesService;

    @GetMapping("/search")
    public ResponseEntity<BaseResponse<List<GameAutocompleteDTO>>> autocomplete(@RequestParam String query) {

        log.info("GamesController - autocomplete called");
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse
                .<List<GameAutocompleteDTO>>builder()
                .success(true)
                .data(gamesService.autocomplete(query))
                .message("Autocomplete results retrieved successfully")
                .build());
    }

    @Operation(summary = "List of games by filters",
            description = "List of games based on the provided search criteria as genre, platform... Supports pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = SUCCESS_SUMMARY,
                                    summary = SUCCESS,
                                    value = LIST_GAME_SUCCESS_RESPONSE
                            ))),
            @ApiResponse(responseCode = "400", description = BAD_REQUEST,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = BAD_REQUEST_SUMMARY,
                                    summary = BAD_REQUEST,
                                    value = LIST_GAME_BAD_REQUEST_RESPONSE
                            ))),
            @ApiResponse(responseCode = "404", description = NOT_FOUND,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = NOT_FOUND_SUMMARY,
                                    summary = NOT_FOUND,
                                    value = LIST_GAME_NOT_FOUND_RESPONSE
                            ))),
            @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = INTERNAL_SERVER_ERROR_SUMMARY,
                                    summary = INTERNAL_SERVER_ERROR,
                                    value = LIST_GAME_INTERNAL_ERROR_RESPONSE
                            )))
    })
    @GetMapping("/search/filter/{filteredGames}")
    public ResponseEntity<BaseResponse<PagedResponse<GameListDTO>>> listGames(@PathVariable List<GameAutocompleteDTO> filteredGames,
                                                                              @RequestParam(defaultValue = "1") int page,
                                                                              @RequestParam(defaultValue = "10") int size) {

        log.info("GamesController - listGames called");
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse
                .<PagedResponse<GameListDTO>>builder()
                .success(true)
                .data(gamesService.listGames(filteredGames, page - 1, size))
                .message("List of games completed successfully")
                .build());
    }

    @Operation(summary = "Searches a game",
            description = "Searches a game for the given name. Supports pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = SUCCESS_SUMMARY,
                                    summary = SUCCESS,
                                    value = SEARCH_GAME_SUCCESS_RESPONSE
                            ))),
            @ApiResponse(responseCode = "400", description = BAD_REQUEST,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = BAD_REQUEST_SUMMARY,
                                    summary = BAD_REQUEST,
                                    value = SEARCH_GAME_BAD_REQUEST_RESPONSE
                            ))),
            @ApiResponse(responseCode = "404", description = NOT_FOUND,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = NOT_FOUND_SUMMARY,
                                    summary = NOT_FOUND,
                                    value = SEARCH_GAME_NOT_FOUND_RESPONSE
                            ))),
            @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = INTERNAL_SERVER_ERROR_SUMMARY,
                                    summary = INTERNAL_SERVER_ERROR,
                                    value = SEARCH_GAME_INTERNAL_ERROR_RESPONSE
                            )))
    })
    @GetMapping("/search/{gameName}")
    public ResponseEntity<BaseResponse<PagedResponse<GameListDTO>>> getGames(@PathVariable String gameName,
                                                                             @RequestParam(defaultValue = "1") int page,
                                                                             @RequestParam(defaultValue = "10") int size) {

        log.info("GamesController - searchGame called");
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse
                .<PagedResponse<GameListDTO>>builder()
                .success(true)
                .data(gamesService.getGames(gameName, page - 1, size))
                .message("Search completed successfully")
                .build());
    }

    @Operation(summary = "Game details",
            description = "Retrieves detailed information about a specific game by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = SUCCESS_SUMMARY,
                                    summary = SUCCESS,
                                    value = GAME_SHEET_SUCCESS_RESPONSE
                            ))),
            @ApiResponse(responseCode = "400", description = BAD_REQUEST,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = BAD_REQUEST_SUMMARY,
                                    summary = BAD_REQUEST,
                                    value = GAME_SHEET_BAD_REQUEST_RESPONSE
                            ))),
            @ApiResponse(responseCode = "404", description = NOT_FOUND,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = NOT_FOUND_SUMMARY,
                                    summary = NOT_FOUND,
                                    value = GAME_SHEET_NOT_FOUND_RESPONSE
                            ))),
            @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = INTERNAL_SERVER_ERROR_SUMMARY,
                                    summary = INTERNAL_SERVER_ERROR,
                                    value = GAME_SHEET_INTERNAL_ERROR_RESPONSE
                            )))
    })
    @GetMapping("/details/{gameId}")
    public ResponseEntity<BaseResponse<GameSheetDTO>> getGameDetails(@PathVariable Integer gameId) {

        log.info("GamesController - getGameDetails called");
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse
                .<GameSheetDTO>builder()
                .success(true)
                .data(gamesService.gameSheet(gameId))
                .message("Game details retrieved successfully")
                .build());

    }
}