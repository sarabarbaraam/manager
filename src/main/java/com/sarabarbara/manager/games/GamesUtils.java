package com.sarabarbara.manager.games;


import com.sarabarbara.manager.games.dtos.GameListDTO;
import com.sarabarbara.manager.games.dtos.GamesInfo;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sarabarbara.manager.shared.constants.APIConstants.APPLICATION_JSON;
import static com.sarabarbara.manager.shared.constants.APIConstants.CONTENT_TYPE;
import static com.sarabarbara.manager.shared.utils.Utils.numberToWord;
import static com.sarabarbara.manager.shared.utils.Utils.toRoman;

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

    // ======================== SEARCH HELPERS ==========================

    public @NotNull @Unmodifiable List<GamesInfo> literalSearch(@NotNull List<GamesInfo> allGames, String query) {

        return allGames.stream()
                .filter(g -> g.name().toLowerCase().contains(query.toLowerCase()))
                .toList();
    }

    public @NotNull @Unmodifiable List<GamesInfo> prefixFallback(@NotNull List<GamesInfo> allGames, @NotNull String query) {

        String first = query.split("\\s+")[0];

        return allGames.stream()
                .filter(g -> g.name().toLowerCase().startsWith(first))
                .toList();
    }

    public List<GamesInfo> numericFallback(List<GamesInfo> allGames, String query) {

        Pattern p = Pattern.compile("(.*?)(\\d+)$");
        Matcher m = p.matcher(query);

        if (!m.find()) return List.of();

        String base = m.group(1);
        int number = Integer.parseInt(m.group(2));

        String roman = toRoman(number);
        String word = numberToWord(number);

        return allGames.stream()
                .filter(g -> {
                    String name = g.name().toLowerCase();
                    return name.contains(base)
                            && (name.contains(String.valueOf(number))
                            || name.contains(roman)
                            || name.contains(word));
                })
                .toList();
    }

    public @NotNull @Unmodifiable List<GamesInfo> fuzzySearch(@NotNull List<GamesInfo> allGames, String query) {

        return allGames.stream()
                .map(g -> new AbstractMap.SimpleEntry<>(g, jaroWinkler(query, g.name().toLowerCase())))
                .filter(e -> e.getValue() >= 0.70)
                .map(AbstractMap.SimpleEntry::getKey)
                .toList();
    }

    // fuzzy search
    private double jaroWinkler(@NotNull String text1, String text2) {

        if (text1.equals(text2)) return 1.0;

        int len1 = text1.length();
        int len2 = text2.length();

        if (len1 == 0 || len2 == 0) return 0.0;

        int matchRange = Math.max(len1, len2) / 2 - 1;

        boolean[] matches1 = new boolean[len1];
        boolean[] matches2 = new boolean[len2];

        int matches = 0;
        int transpositions = 0;

        for (int i = 0; i < len1; i++) {

            int start = Math.max(0, i - matchRange);
            int end = Math.min(i + matchRange + 1, len2);

            for (int j = start; j < end; j++) {
                if (matches2[j]) continue;
                if (text1.charAt(i) != text2.charAt(j)) continue;

                matches1[i] = true;
                matches2[j] = true;
                matches++;
                break;
            }
        }

        if (matches == 0) return 0.0;

        int k = 0;
        for (int i = 0; i < len1; i++) {

            if (!matches1[i]) continue;
            while (!matches2[k]) k++;
            if (text1.charAt(i) != text2.charAt(k)) transpositions++;
            k++;
        }

        double m = matches;
        double jaro = ((m / len1) + (m / len2) + ((m - transpositions / 2.0) / m)) / 3.0;

        int prefix = 0;
        for (int i = 0; i < Math.min(4, Math.min(len1, len2)); i++) {

            if (text1.charAt(i) == text2.charAt(i)) prefix++;
            else break;
        }

        return jaro + prefix * 0.1 * (1 - jaro);
    }

}
