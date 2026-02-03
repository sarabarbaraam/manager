package com.sarabarbara.manager.infrastructure.external.steam;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sarabarbara.manager.games.dtos.*;
import com.sarabarbara.manager.shared.exceptions.ExternalApiException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sarabarbara.manager.games.GamesUtils.normalizeName;
import static com.sarabarbara.manager.games.GamesUtils.normalizeQuery;
import static com.sarabarbara.manager.infrastructure.external.steam.SteamAPIConstants.GET_ALL_GAMES;
import static com.sarabarbara.manager.shared.utils.Utils.*;

/**
 * SteamClient class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 13/01/2026
 */

@Slf4j
@Component
@AllArgsConstructor
public class SteamClient {

    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private final ObjectMapper mapper;
    private final SteamConfig steamConfig;

    CacheManager cacheManager;


    @Cacheable(value = "steamSearch", key = "#gameName.toLowerCase()")
    public List<GameSearchDTO> searchGame(String gameName) {

        log.info("SteamClient - searchGames called");
        log.debug("Searching for game: {}", gameName);
        log.debug("Searching...");

        cacheManager.getCacheNames().forEach(name -> Objects.requireNonNull(cacheManager.getCache(name)).clear());
        List<GamesInfo> allGames = loadAllGames();

        String normalizeGameName = normalizeQuery(gameName);
        List<GamesInfo> normalizedAllGames = allGames.stream()
                .map(g -> new GamesInfo(g.appid(), normalizeName(g.name())))
                .toList();

        // add local search in Steam app list
        List<GameSearchDTO> candidates = new ArrayList<>();

        candidates.addAll(literalSearch(normalizedAllGames, normalizeGameName));
        candidates.addAll(prefixFallback(normalizedAllGames, normalizeGameName));
        candidates.addAll(numericFallback(normalizedAllGames, normalizeGameName));
        candidates.addAll(fuzzySearch(normalizedAllGames, normalizeGameName));

        // add Steam Store search results
        candidates.addAll(searchSteamStore(normalizeGameName));

        // add SteamDB search results
        StoreSearchResponseDTO db = searchGameFromSteamDb(gameName);

        if (db.success()) {

            candidates.addAll(db.data().content());
        }

        return getGameRanking(candidates, normalizeGameName);
    }

    private @NotNull List<GameSearchDTO> getGameRanking(@NotNull List<GameSearchDTO> candidates, String normalizedQuery) {

        Map<Integer, GameSearchDTO> merged = new LinkedHashMap<>();

        for (GameSearchDTO dto : candidates) merged.put(dto.id(), dto);

        List<GameSearchDTO> finalList = new ArrayList<>(merged.values());

        // sort by relevance
        finalList.sort((a, b) -> {

            int scoreA = relevanceScore(normalizedQuery, normalizeName(a.name()));
            int scoreB = relevanceScore(normalizedQuery, normalizeName(b.name()));

            log.warn("COMPARE '{}' (score={})  vs  '{}' (score={})",
                    a.name(), scoreA, b.name(), scoreB);

            return Integer.compare(scoreB, scoreA);
        });
        return finalList;
    }

    // ============================= Complementary methods =============================

    @Cacheable("steamAllGames")
    public List<GamesInfo> loadAllGames() {
        try {
            String url = GET_ALL_GAMES.formatted(steamConfig.getApiKey());

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(10))
                    .header("Accept", "application/json")
                    .header("User-Agent", "Mozilla/5.0")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body().trim();

            if (body.startsWith("<")) {
                throw new ExternalApiException("Steam returned HTML instead of JSON");
            }

            JsonNode apps = mapper.readTree(body)
                    .path("response")
                    .path("apps");

            if (!apps.isArray()) {
                throw new ExternalApiException("Invalid JSON structure from Steam");
            }

            List<GamesInfo> list = new ArrayList<>();

            for (JsonNode app : apps) {
                int id = app.path("appid").asInt();
                String name = app.path("name").asText();
                if (!name.isBlank()) {
                    list.add(new GamesInfo(id, name));
                }
            }

            log.info("Loaded {} games from Steam", list.size());
            return list;

        } catch (Exception e) {
            throw new ExternalApiException("Error loading Steam app list", e);
        }
    }

    private int relevanceScore(String query, @NotNull String name) {

        // exact match
        if (name.equals(query)) return 1500;

        // starts with
        if (name.startsWith(query)) return 1200;

        // base name match (remove trailing numbers)
        String nameWithoutNumber = name.replaceAll("\\d+$", "").trim();
        if (nameWithoutNumber.equals(query)) return 1100;

        // detect base + number

        Pattern p = Pattern.compile("(.*?)(\\d+)$");
        Matcher m = p.matcher(query);

        if (m.find()) {

            String base = m.group(1).trim();
            int number;
            try {
                number = Integer.parseInt(m.group(2));
            } catch (NumberFormatException e) {
                log.warn("Failed to parse number from query '{}': {}", query, e.getMessage());
                return -1000;
            }
            String roman = toRoman(number);
            String word = numberToWord(number);

            boolean containsBase = name.contains(base);
            boolean containsArabic = name.contains(String.valueOf(number));
            boolean containsRoman = name.contains(roman);
            boolean containsWord = name.contains(word);

            if (containsBase && (containsArabic || containsRoman || containsWord)) return 2000;

            if (containsBase) return 800;

            if (containsArabic || containsRoman || containsWord)
                return -500;
        }

        return -1000;
    }

    private @NotNull @Unmodifiable List<GameSearchDTO> literalSearch(@NotNull List<GamesInfo> allGames, @NotNull String query) {

        return allGames.stream()
                .filter(g -> g.name().toLowerCase().contains(query.toLowerCase()))
                .map(this::toSearchDTO)
                .toList();
    }

    private @NotNull @Unmodifiable List<GameSearchDTO> prefixFallback(@NotNull List<GamesInfo> allGames, @NotNull String query) {

        String first = query.split("\\s+")[0];

        return allGames.stream()
                .filter(g -> g.name().toLowerCase().startsWith(first))
                .map(this::toSearchDTO)
                .toList();
    }

    private List<GameSearchDTO> numericFallback(List<GamesInfo> allGames, String query) {

        Pattern p = Pattern.compile("(.*?)(\\d+)$");
        Matcher m = p.matcher(query);

        if (!m.find()) return List.of();

        String base = m.group(1);
        int number;
        try {
            number = Integer.parseInt(m.group(2));
        } catch (NumberFormatException e) {
            log.warn("Failed to parse number from query '{}': {}", query, e.getMessage());
            return List.of();
        }

        String roman = toRoman(number);
        String word = numberToWord(number);

        return allGames.stream()
                .filter(g -> {

                    String name = g.name().toLowerCase();

                    if (!name.contains(base)) return false;

                    return name.contains(String.valueOf(number))
                            || name.contains(roman)
                            || name.contains(word);
                })
                .map(this::toSearchDTO)
                .toList();
    }

    private @NotNull @Unmodifiable List<GameSearchDTO> fuzzySearch(@NotNull List<GamesInfo> allGames, String query) {

        return allGames.stream()
                .map(g -> new AbstractMap.SimpleEntry<>(g, jaroWinkler(query, g.name().toLowerCase())))
                .filter(e -> e.getValue() >= 0.70)
                .map(e -> toSearchDTO(e.getKey()))
                .toList();
    }

    @Contract("_ -> new")
    private @NotNull GameSearchDTO toSearchDTO(@NotNull GamesInfo game) {
        return new GameSearchDTO(
                game.appid(),
                game.name(),
                "https://cdn.akamai.steamstatic.com/steam/apps/" + game.appid() + "/capsule_184x69.jpg"
        );
    }

    public StoreSearchResponseDTO searchGameFromSteamDb(String query) {

        String url = "https://steamdb.info/api/SteamRailgun/?q=" +
                URLEncoder.encode(query, StandardCharsets.UTF_8);

        int maxRetries = 3;

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("Accept", "application/json")
                        .header("Accept-Encoding", "gzip")
                        .header("User-Agent",
                                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 "
                                        + "(KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                        .timeout(Duration.ofSeconds(10))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                String body = response.body().trim();

                // SteamDB sometimes returns HTML when rate-limited
                if (!body.startsWith("{")) {
                    log.warn("SteamDB returned non-JSON content on attempt {}: {}", attempt, body.substring(0, Math.min(200, body.length())));
                    Thread.sleep(300L * attempt);
                    continue;
                }

                // Parse JSON
                JsonNode root = mapper.readTree(body);

                JsonNode itemsNode = root.path("data").path("results");

                List<SteamDbResultDTO> rawResults = mapper.convertValue(
                        itemsNode,
                        mapper.getTypeFactory().constructCollectionType(List.class, SteamDbResultDTO.class)
                );

                List<GameSearchDTO> results = rawResults.stream()
                        .map(r -> new GameSearchDTO(
                                r.id(),
                                r.name(),
                                r.tinyImage()
                        ))
                        .toList();

                StoreSearchData data = new StoreSearchData(
                        results,
                        results.size(),
                        1,
                        0,
                        results.size()
                );

                return new StoreSearchResponseDTO(
                        true,
                        data,
                        "Search completed successfully"
                );

            } catch (Exception e) {
                log.error("Error searching SteamDB (attempt {}): {}", attempt, e.getMessage());
            }
        }

        // If all retries fail
        return new StoreSearchResponseDTO(
                false,
                new StoreSearchData(List.of(), 0, 0, 0, 0),
                "Error searching game in SteamDB"
        );
    }

    @Cacheable(value = "steamStoreSearch", key = "#query.toLowerCase()")
    public List<GameSearchDTO> searchSteamStore(String query) {

        try {
            String url = "https://store.steampowered.com/api/storesearch/?term=" +
                    URLEncoder.encode(query, StandardCharsets.UTF_8) +
                    "&l=english&cc=US";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(10))
                    .header("Accept", "application/json")
                    .header("User-Agent", "Mozilla/5.0")
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            String body = response.body().trim();

            if (!body.startsWith("{")) {
                log.warn("Steam Store returned non-JSON content: {}", body.substring(0, Math.min(200, body.length())));
                return List.of();
            }

            JsonNode root = mapper.readTree(body);
            JsonNode items = root.path("items");

            if (!items.isArray()) {
                log.warn("Steam Store returned invalid structure: {}", body);
                return List.of();
            }

            List<GameSearchDTO> results = new ArrayList<>();

            for (JsonNode item : items) {
                int id = item.path("id").asInt();
                String name = item.path("name").asText();
                String image = item.path("tiny_image").asText();

                if (id > 0 && !name.isBlank()) {
                    results.add(new GameSearchDTO(id, name, image));
                }
            }

            return results;

        } catch (Exception e) {
            log.error("Error searching Steam Store: {}", e.getMessage());
            return List.of();
        }
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



