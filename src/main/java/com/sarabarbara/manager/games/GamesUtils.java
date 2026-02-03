package com.sarabarbara.manager.games;


import com.sarabarbara.manager.games.dtos.GameListDTO;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

import static com.sarabarbara.manager.shared.constants.APIConstants.APPLICATION_JSON;
import static com.sarabarbara.manager.shared.constants.APIConstants.CONTENT_TYPE;

/**
 * Utils class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 13/01/2026
 */

@Slf4j
@Component
public class GamesUtils {

    public static @NonNull String cleanInvalidJsonCharacters(@NonNull String string) {

        return string.replaceAll("[\\x00-\\x1F\\x7F\\uFFFD]", "");
    }

    public static boolean isValidJson(@NonNull HttpResponse<String> response, String cleanedResponseBody,
                                      Integer gameId,
                                      String responseBody) {

        String contentType = response.headers().firstValue(CONTENT_TYPE).orElse("");

        if (!contentType.contains(APPLICATION_JSON)) {

            log.error("Unexpected content type: {}", contentType);
            return true;
        }

        if (!cleanedResponseBody.trim().startsWith("{") && !cleanedResponseBody.trim().startsWith("[")) {

            log.error("Response is not a valid JSON for gameId {}: {}", gameId, responseBody);
            return true;
        }

        if (responseBody.trim().isEmpty()) {

            log.error("Response body is empty for gameId {}: {}", gameId, responseBody);
            return true;
        }


        if (responseBody.contains("\"success\":false")) {

            log.error("Game details not found. Response: {}", responseBody);
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

    public static void gameType(@NonNull GameListDTO games, List<GameListDTO> gamesList) {

        if (games.type() != null && ("game".equalsIgnoreCase(games.type()) || "dlc".equalsIgnoreCase(games.type()))) {

            gamesList.add(games);
        }
    }

    /**
     * Normalize query string for better matching in searches from user input.
     *
     * @param query the query string
     * @return the normalized query string
     */

    public static @NotNull String normalizeQuery(String query) {

        query = normalize(query);
        query = wordToNumber(query);
        query = romanToIntInText(query);

        return query;
    }

    /**
     * Normalize name string for better matching in searches from external sources.
     *
     * @param n the name string
     * @return the normalized name string
     */

    public static @NotNull String normalizeName(String n) {

        n = normalize(n);
        n = wordToNumber(n);
        n = romanToIntInText(n);

        return n;
    }

    private static @NotNull String normalize(@NotNull String text) {

        return text
                .toLowerCase()
                .replaceAll("[™®©]", "")
                .replaceAll("[:\\-]", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }

    private static String wordToNumber(String text) {

        Map<String, String> map = Map.of(
                "one", "1",
                "two", "2",
                "three", "3",
                "four", "4",
                "five", "5",
                "six", "6",
                "seven", "7",
                "eight", "8",
                "nine", "9",
                "ten", "10"
        );

        for (var entry : map.entrySet()) {
            text = text.toLowerCase().trim().replaceAll("(?<![a-z])" + entry.getKey() + "(?![a-z])", entry.getValue());
        }

        return text;
    }

    @Contract(pure = true)
    private static @NotNull String romanToIntInText(@NotNull String text) {
        return text
                .replaceAll("(?i)\\biii\\b", "3")
                .replaceAll("(?i)\\bii\\b", "2")
                .replaceAll("(?i)\\bi\\b", "1")
                .replaceAll("(?i)\\biv\\b", "4")
                .replaceAll("(?i)\\bv\\b", "5")
                .replaceAll("(?i)\\bvi\\b", "6")
                .replaceAll("(?i)\\bvii\\b", "7")
                .replaceAll("(?i)\\bviii\\b", "8")
                .replaceAll("(?i)\\bix\\b", "9")
                .replaceAll("(?i)\\bx\\b", "10");
    }

}
