package com.sarabarbara.manager.infrastructure.external.steam;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sarabarbara.manager.games.GamesMapper;
import com.sarabarbara.manager.games.GamesUtils;
import com.sarabarbara.manager.games.SteamBatchParser;
import com.sarabarbara.manager.games.dtos.GameListDTO;
import com.sarabarbara.manager.games.dtos.GamesInfo;
import com.sarabarbara.manager.games.dtos.SteamStoreDTO;
import com.sarabarbara.manager.shared.exceptions.ExternalApiException;
import com.sarabarbara.manager.shared.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.sarabarbara.manager.games.GamesUtils.normalizeName;
import static com.sarabarbara.manager.games.GamesUtils.normalizeQuery;
import static com.sarabarbara.manager.infrastructure.external.steam.SteamAPIConstants.*;
import static com.sarabarbara.manager.shared.utils.Utils.numberToWord;
import static com.sarabarbara.manager.shared.utils.Utils.toRoman;

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
    private final GamesUtils gamesUtils;
    private final GamesMapper gamesMapper;
    private final Executor steamDetailsExecutor;
    private final Utils utils;
    private final SteamBatchParser steamBatchParser;

    // =========================== SEARCH GAME (CUADRÍCULA) ====================

    public List<GameListDTO> searchGame(List<GamesInfo> allGames, String gameName) {

        long searchStart = System.currentTimeMillis();
        String normalized = normalizeQuery(gameName);

        // ========== LOCAL SEARCH ==========
        List<GamesInfo> localCandidates = new ArrayList<>();
        localCandidates.addAll(gamesUtils.literalSearch(allGames, normalized));
        localCandidates.addAll(gamesUtils.prefixFallback(allGames, normalized));
        localCandidates.addAll(gamesUtils.numericFallback(allGames, normalized));
        localCandidates.addAll(gamesUtils.fuzzySearch(allGames, normalized));

        List<GameListDTO> localMapped = localCandidates.stream()
                .map(gamesMapper::toGameListDTOFromGamesInfo)
                .toList();

        // ========== REMOTE SEARCH ==========
        List<GameListDTO> remoteCandidates = searchSteamStore(normalized).stream()
                .map(gamesMapper::toGameListDTOFromSteamStore)
                .toList();

        // ========== MERGE ==========
        List<GameListDTO> all = Stream.concat(
                localMapped.stream(),
                remoteCandidates.stream()
        ).toList();

        if (all.isEmpty()) {
            return List.of();
        }

        // ========== BATCH DETAILS ==========
        List<Integer> ids = all.stream()
                .map(GameListDTO::id)
                .toList();

        List<GameListDTO> fullDetails = getGameDetailsBatch(ids);

        // ========== RANKING ==========
        List<GameListDTO> ranked = getGameRanking(fullDetails, normalized);

        long searchEnd = System.currentTimeMillis();
        log.warn("SEARCH took {} ms", (searchEnd - searchStart));

        return ranked;
    }


    // ============================= GAME DETAILS ===========================

    /*
    @Cacheable(value = "steamAppDetails", key = "#appId")
    public GameListDTO getAppDetails(int appId) {

    }
     */
    // ============================= Complementary methods =============================

    // ============================= STEAM API CALLS =============================
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

                log.error("Steam returned HTML instead of JSON: {}", body);
                throw new ExternalApiException("Steam returned HTML instead of JSON");
            }

            JsonNode apps = mapper.readTree(body)
                    .path("response")
                    .path("apps");

            if (!apps.isArray()) {

                log.error("Invalid JSON structure from Steam: {}", body);
                throw new ExternalApiException("Invalid JSON structure from Steam");
            }

            List<GamesInfo> list = new ArrayList<>();

            for (JsonNode app : apps) {

                int id = app.path("appid").asInt();
                String name = app.path("name").asText();
                String image = "https://cdn.akamai.steamstatic.com/steam/apps/"
                        + id + "/capsule_184x69.jpg";

                if (!name.isBlank()) {

                    list.add(new GamesInfo(id, name, image));
                }
            }

            log.info("Loaded {} games from Steam", list.size());
            return list;

        } catch (Exception e) {

            log.error("Error loading Steam app list: {}", e.getMessage());
            throw new ExternalApiException("Error loading Steam app list", e);
        }
    }

    @Cacheable(value = "steamStoreSearch", key = "#query.toLowerCase()")
    public List<SteamStoreDTO> searchSteamStore(String query) {

        try {

            String url = GET_GAMES_STEAM_STORE +
                    URLEncoder.encode(query, StandardCharsets.UTF_8) +
                    L_ENGLISH +
                    CC_US;

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

            List<SteamStoreDTO> results = new ArrayList<>();

            for (JsonNode item : items) {

                int id = item.path("id").asInt();
                String name = item.path("name").asText();
                String image = item.path("tiny_image").asText();
                String type = item.path("type").asText();


                if (id > 0 && !name.isBlank()) {

                    results.add(new SteamStoreDTO(id, name, image, type));
                }
            }

            return results;

        } catch (Exception e) {

            log.error("Error searching Steam Store: {}", e.getMessage());
            return List.of();
        }
    }

    @Cacheable(
            value = "steamGameDetailsBatch",
            key = "#ids.hashCode()",
            unless = "#result == null || #result.isEmpty()"
    )
    public List<GameListDTO> getGameDetailsBatch(@NotNull List<Integer> ids) {

        long start = System.currentTimeMillis();

        // URL base con parámetros correctos
        final String urlTemplate =
                "https://store.steampowered.com/api/appdetails?appids=%s";

        // 1. Particionar en batches de 10
        List<List<Integer>> batches = utils.partition(ids, 10);

        // 2. Ejecutar batches en paralelo
        List<CompletableFuture<Map<Integer, JsonNode>>> futures =
                batches.stream()
                        .map(batch -> CompletableFuture.supplyAsync(
                                () -> {
                                    // Convertir IDs → "id1,id2,id3"
                                    String joined = batch.stream()
                                            .map(String::valueOf)
                                            .collect(Collectors.joining(","));

                                    // Construir URL final
                                    String url = urlTemplate.formatted(joined);

                                    return utils.fetchBatch(batch, url, steamBatchParser);
                                },
                                steamDetailsExecutor
                        ))
                        .toList();

        // 3. Unir resultados
        Map<Integer, JsonNode> merged =
                futures.stream()
                        .map(CompletableFuture::join)
                        .flatMap(m -> m.entrySet().stream())
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (a, b) -> a, // evitar colisiones
                                LinkedHashMap::new
                        ));

        // 4. Convertir a DTO
        List<GameListDTO> results = merged.values().stream()
                .map(node -> mapper.convertValue(node, GameListDTO.class))
                .toList();

        log.warn("DETAILS BATCH took {} ms", (System.currentTimeMillis() - start));

        return results;
    }

    /**
     * Fetches game details from Steam with save retries and error handling.
     *
     * @param id Game ID
     * @return GameListDTO or null if not found/error
     */

    private @Nullable GameListDTO fetchDetailsSafe(@NotNull Integer id) {

        String url = GET_GAME + id + L_ENGLISH + CC_US;


        try {

            // to avoid hitting Steam too hard, especially if we get non-JSON responses
            Thread.sleep(250);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(10))
                    .header("Accept", "application/json")
                    .header("User-Agent",
                            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 "
                                    + "(KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36")
                    .header("Cookie", "birthtime=0; lastagecheckage=1-0-1900")
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            String body = response.body().trim();

            if (!body.startsWith("{")) {

                log.warn("Steam returned non-JSON for appid {}: {}",
                        id, body.substring(0, Math.min(200, body.length())));

                Thread.sleep(150L + new Random().nextInt(100)); // random backoff to reduce chances of repeated blocks
            }

            JsonNode root = mapper.readTree(body);
            JsonNode dataNode = root.path(String.valueOf(id)).path("data");

            if (dataNode.isMissingNode() || dataNode.isNull()) {
                log.warn("Steam returned empty data for appid {}", id);
                return null;
            }

            return mapper.convertValue(dataNode, GameListDTO.class);

        } catch (Exception e) {

            log.error("Error fetching details for game ID {}: {}",
                    id, e.getMessage());
        }

        return null;
    }

    // ============================= RANKING ALGORITHM =============================

    private @NotNull List<GameListDTO> getGameRanking(@NotNull List<GameListDTO> candidates, String normalizedQuery) {

        Map<Integer, GameListDTO> merged = new LinkedHashMap<>();

        for (GameListDTO dto : candidates) merged.put(dto.id(), dto);

        List<GameListDTO> finalList = new ArrayList<>(merged.values());

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

                log.warn("Failed to parse number '{}' from query '{}': {}", m.group(2), query, e.getMessage());
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

}
