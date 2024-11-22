package com.sarabarbara.manager.apis;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sarabarbara.manager.models.games.Games;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sarabarbara.manager.constants.Constants.APPLICATION_JSON;
import static com.sarabarbara.manager.constants.Constants.CONTENT_TYPE;
import static com.sarabarbara.manager.constants.SteamConstants.GET_GAMES_LIST;
import static com.sarabarbara.manager.constants.SteamConstants.GET_GAME_DETAIL;
import static com.sarabarbara.manager.utils.GamesUtils.*;

/**
 * SteamApi class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 20/11/2024
 */

@Component
@AllArgsConstructor
@NoArgsConstructor
public class SteamAPI {

    private static final Logger logger = LoggerFactory.getLogger(SteamAPI.class);

    private HttpClient client;
    private ObjectMapper mapper;

    /**
     * Get the games of the Steam API
     *
     * @param gameName the gameName
     *
     * @return the appid and the name
     */

    public Map<Integer, String> getGamesList(String gameName) {

        Map<Integer, String> foundGames = new HashMap<>();

        try {

            client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(GET_GAMES_LIST))
                    .header(CONTENT_TYPE, APPLICATION_JSON)
                    .header("Accept", "application/json; charset=UTF-8")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            mapper = new ObjectMapper();

            JsonNode appList = mapper.readTree(response.body())
                    .path("applist")
                    .path("apps");

            for (JsonNode app : appList) {

                String appName = app.get("name").asText();
                int appId = app.get("appid").asInt();

                if (appName.toLowerCase().contains(gameName.toLowerCase())) {

                    foundGames.put(appId, appName);
                }
            }

        } catch (IOException e) {

            logger.error("IOException occurred while making the request: {}", e.getMessage(), e);

        } catch (InterruptedException e) {

            logger.error("Thread was interrupted during the request: {}", e.getMessage(), e);
            Thread.currentThread().interrupt();
        }

        return foundGames;
    }

    /**
     * Get the details of the game
     *
     * @param searchedGame the searchedGame
     *
     * @return the game
     */

    public List<Games> getGame(@NonNull Map<Integer, String> searchedGame) {

        List<Games> gamesList = new ArrayList<>();

        try {

            client = HttpClient.newHttpClient();
            mapper = new ObjectMapper();

            for (Map.Entry<Integer, String> entry : searchedGame.entrySet()) {

                Integer gameId = entry.getKey();

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(GET_GAME_DETAIL + gameId + "&l=spanish"))
                        .header(CONTENT_TYPE, APPLICATION_JSON)
                        .header("Accept", "application/json; charset=UTF-8")
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                logger.info("API Response for gameId {}: {}", gameId, response);

                String responseBody = response.body();
                String cleanedResponseBody = cleanInvalidJsonCharacters(responseBody);

                if (isValidJson(response, cleanedResponseBody, gameId, responseBody)) continue;

                mapper.configure(JsonReadFeature.ALLOW_LEADING_PLUS_SIGN_FOR_NUMBERS.mappedFeature(), true);

                JsonNode gameTree = mapper.readTree(cleanedResponseBody).path(String.valueOf(gameId)).path("data");

                logger.info("Body: {}", responseBody);

                Games games = mapper.treeToValue(gameTree, Games.class);

                gameType(games, gamesList);

            }

        } catch (IOException e) {


            logger.error("IOException occurred while making the request: {}", e.getMessage(), e);

        } catch (InterruptedException e) {

            logger.error("Thread was interrupted during the request: {}", e.getMessage(), e);
            Thread.currentThread().interrupt();
        }

        return gamesList;
    }

    private static boolean isValidJson(HttpResponse<String> response, String cleanedResponseBody, Integer gameId, String responseBody) {

        String contentType = response.headers().firstValue(CONTENT_TYPE).orElse("");
        if (!contentType.contains(APPLICATION_JSON)) {

            logger.error("Unexpected content type: {}", contentType);
            return true;
        }

        if (!cleanedResponseBody.trim().startsWith("{") && !cleanedResponseBody.trim().startsWith("[")) {

            logger.error("Response is not a valid JSON for gameId {}: {}", gameId, responseBody);
            return true;
        }

        if (responseBody.trim().isEmpty()) {
            logger.error("Response body is empty for gameId {}: {}", gameId, responseBody);
            return true;
        }


        if (responseBody.contains("\"success\":false")) {

            logger.error("Game details not found. Response: {}", responseBody);
            return true;
        }
        return false;
    }

    /**
     * If the type of the app is a game or a dlc it will add to the list
     *
     * @param games     the games
     * @param gamesList the games list
     */
    private static void gameType(Games games, List<Games> gamesList) {

        if (games.getType() != null && "game".equalsIgnoreCase(games.getType()) || "dlc".equalsIgnoreCase(games.getType())) {

            gamesList.add(games);
        }
    }

}
