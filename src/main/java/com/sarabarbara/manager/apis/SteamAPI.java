package com.sarabarbara.manager.apis;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sarabarbara.manager.config.SteamConfig;
import com.sarabarbara.manager.models.games.Achievement;
import com.sarabarbara.manager.models.games.AchievementDetails;
import com.sarabarbara.manager.models.games.Games;
import com.sarabarbara.manager.models.games.Ratings;
import lombok.AllArgsConstructor;
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

import static com.sarabarbara.manager.constants.Constants.*;
import static com.sarabarbara.manager.constants.SteamConstants.*;
import static com.sarabarbara.manager.utils.GamesUtils.cleanInvalidJsonCharacters;

/**
 * SteamApi class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 20/11/2024
 */

@Component
@AllArgsConstructor
public class SteamAPI {

    private static final Logger logger = LoggerFactory.getLogger(SteamAPI.class);

    private HttpClient client;
    private ObjectMapper mapper;
    private final SteamConfig steamConfig;

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
                    .header(ACCEPT, APPLICATION_JSON_CHARSET_UTF_8)
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

            logger.error(IOEXCEPTION, e.getMessage(), e);

        } catch (InterruptedException e) {

            logger.error(THREAD_WAS_INTERRUPTED_DURING_THE_REQUEST, e.getMessage(), e);
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
                        .uri(URI.create(GET_GAME_DETAIL + gameId + L_SPANISH))
                        .header(CONTENT_TYPE, APPLICATION_JSON)
                        .header(ACCEPT, APPLICATION_JSON_CHARSET_UTF_8)
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                logger.info("API Response for gameId {}: {}", gameId, response);

                String responseBody = response.body();
                String cleanedResponseBody = cleanInvalidJsonCharacters(responseBody);

                if (isValidJson(response, cleanedResponseBody, gameId, responseBody)) continue;

                mapper.configure(JsonReadFeature.ALLOW_LEADING_PLUS_SIGN_FOR_NUMBERS.mappedFeature(), true);

                JsonNode gameTree = mapper.readTree(cleanedResponseBody)
                        .path(String.valueOf(gameId))
                        .path("data");

                logger.info(BODY, responseBody);

                Games games = mapper.treeToValue(gameTree, Games.class);

                gameType(games, gamesList);

            }

        } catch (IOException e) {

            logger.error(IOEXCEPTION, e.getMessage(), e);

        } catch (InterruptedException e) {

            logger.error(THREAD_WAS_INTERRUPTED_DURING_THE_REQUEST, e.getMessage(), e);
            Thread.currentThread().interrupt();
        }

        return gamesList;
    }

    /**
     * Get the game sheet with the information about the game
     *
     * @param gameId the gameId
     *
     * @return the information about the game
     */

    public Games getGameSheet(Integer gameId) {

        try {

            client = HttpClient.newHttpClient();
            final String apiKey = steamConfig.getApiKey();

            mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

            // game's information
            HttpRequest requestGameInformation = HttpRequest.newBuilder()
                    .uri(URI.create(GET_GAME_DETAIL + gameId + L_SPANISH))
                    .header(CONTENT_TYPE, APPLICATION_JSON)
                    .header(ACCEPT, APPLICATION_JSON_CHARSET_UTF_8)
                    .build();

            HttpResponse<String> responseGameInformation =
                    client.send(requestGameInformation, HttpResponse.BodyHandlers.ofString());

            String infoGameBody = responseGameInformation.body();
            logger.info("Game information: {}", infoGameBody);

            JsonNode gameTree = mapper.readTree(infoGameBody)
                    .path(String.valueOf(gameId))
                    .path("data");

            Games game = mapper.treeToValue(gameTree, Games.class);

            // achievement's information
            HttpRequest requestAchievementsInformation = HttpRequest.newBuilder()
                    .uri(URI.create(GET_GAME_ACHIEVEMENT + "?key=" + apiKey + "&appid=" + gameId))
                    .header(CONTENT_TYPE, APPLICATION_JSON)
                    .header(ACCEPT, APPLICATION_JSON_CHARSET_UTF_8)
                    .build();

            HttpResponse<String> responseAchievementsInformation =
                    client.send(requestAchievementsInformation, HttpResponse.BodyHandlers.ofString());

            String infoAchievementBody = responseAchievementsInformation.body();
            logger.info("Achievements information: {}", infoAchievementBody);

            mapper.configure(JsonReadFeature.ALLOW_LEADING_PLUS_SIGN_FOR_NUMBERS.mappedFeature(), true);

            JsonNode achievementTree = mapper.readTree(infoAchievementBody)
                    .path("game")
                    .path("availableGameStats")
                    .path("achievements");

            if (achievementTree.isArray()) {

                List<AchievementDetails> achievementDetailsList;
                achievementDetailsList = mapper.convertValue(
                        achievementTree,
                        new TypeReference<>() {

                        }
                );

                Achievement achievement =
                        Achievement.builder().total(game.getAchievements().getTotal())
                                .achievements(achievementDetailsList).build();

                game.setAchievements(achievement);

            }

            // Rating information

            HttpRequest requestRatingInformation = HttpRequest.newBuilder()
                    .uri(URI.create(GET_GAME_RATING + gameId + "?json=1"))
                    .header(CONTENT_TYPE, APPLICATION_JSON)
                    .header(ACCEPT, APPLICATION_JSON_CHARSET_UTF_8)
                    .build();

            HttpResponse<String> responseRatingInformation = client.send(requestRatingInformation,
                    HttpResponse.BodyHandlers.ofString());

            String infoRatingBody = responseRatingInformation.body();
            logger.info("Rating information: {}", infoRatingBody);

            JsonNode ratingTree = mapper.readTree(infoRatingBody)
                    .path("query_summary");

            Ratings rating = mapper.treeToValue(ratingTree, Ratings.class);

            game.setRatings(rating);

            return game;

        } catch (IOException e) {

            logger.error(IOEXCEPTION, e.getMessage(), e);

        } catch (InterruptedException e) {

            logger.error(THREAD_WAS_INTERRUPTED_DURING_THE_REQUEST, e.getMessage(), e);
            Thread.currentThread().interrupt();
        }

        return new Games();
    }

    // Complementary methods

    /**
     * Checks if the return of the json is valid
     *
     * @param response            the response
     * @param cleanedResponseBody the cleanedResponseBody
     * @param gameId              the gameId
     * @param responseBody        the responseBody
     *
     * @return true or false
     */

    private static boolean isValidJson(@NonNull HttpResponse<String> response, String cleanedResponseBody,
                                       Integer gameId,
                                       String responseBody) {

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

    private static void gameType(@NonNull Games games, List<Games> gamesList) {

        if (games.getType() != null && "game" .equalsIgnoreCase(games.getType()) || "dlc" .equalsIgnoreCase(games.getType())) {

            gamesList.add(games);
        }
    }

}
