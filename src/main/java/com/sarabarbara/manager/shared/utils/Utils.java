package com.sarabarbara.manager.shared.utils;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Utils class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 03/02/2026
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class Utils {

    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    private final ObjectMapper mapper;

    @Contract(pure = true)
    public static @NotNull String toRoman(int number) {

        return switch (number) {
            case 1 -> "i";
            case 2 -> "ii";
            case 3 -> "iii";
            case 4 -> "iv";
            case 5 -> "v";
            case 6 -> "vi";
            case 7 -> "vii";
            case 8 -> "viii";
            case 9 -> "ix";
            case 10 -> "x";
            default -> String.valueOf(number);
        };
    }

    @Contract(pure = true)
    public static @NotNull String numberToWord(int number) {

        return switch (number) {
            case 1 -> "one";
            case 2 -> "two";
            case 3 -> "three";
            case 4 -> "four";
            case 5 -> "five";
            case 6 -> "six";
            case 7 -> "seven";
            case 8 -> "eight";
            case 9 -> "nine";
            case 10 -> "ten";
            default -> "";
        };
    }

    // Utility method to partition a list into sublists of a given size for batch processing
    @NotNull
    public List<List<Integer>> partition(@NotNull List<Integer> ids, int size) {

        List<List<Integer>> parts = new ArrayList<>();

        for (int i = 0; i < ids.size(); i += size) {

            parts.add(ids.subList(i, Math.min(i + size, ids.size())));
        }

        return parts;
    }

    public <T> Map<Integer, T> fetchBatch(
            @NotNull List<Integer> ids,
            String urlPrefix,
            BatchResponseParser<T> parser
    ) {

        String joined = ids.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        String url = urlPrefix;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(10))
                .header("Accept", "application/json")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                .header("Cookie", "birthtime=0; lastagecheckage=1-0-1900")
                .build();

        try {
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            String body = response.body().trim();

            if (!body.startsWith("{")) {

                log.warn("API returned non-JSON for batch {}", joined);
                return Map.of();
            }

            JsonNode root = mapper.readTree(body);
            return parser.parse(root, ids);

        } catch (Exception e) {

            log.error("Error fetching batch {}: {}", joined, e.getMessage());
            return Map.of();
        }
    }

}
