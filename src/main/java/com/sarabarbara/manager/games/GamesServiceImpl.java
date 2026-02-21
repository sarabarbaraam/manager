package com.sarabarbara.manager.games;


import com.sarabarbara.manager.games.dtos.*;
import com.sarabarbara.manager.infrastructure.external.steam.SteamClient;
import com.sarabarbara.manager.shared.PagedResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.sarabarbara.manager.games.GamesUtils.normalizeQuery;

/**
 * GamesServiceImpl class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 13/01/2026
 */

@Slf4j
@Transactional
@Service
public class GamesServiceImpl implements GamesService {

    private final SteamClient steamClient;
    private final Executor steamExecutor;
    private final GamesUtils gamesUtils;
    private final GamesMapper mapper;
    private final SteamBatchParser steamBatchParser;

    public GamesServiceImpl(@Qualifier("steamDetailsExecutor") Executor steamExecutor, SteamClient steamClient, GamesUtils gamesUtils, GamesMapper mapper, SteamBatchParser steamBatchParser) {
        this.steamClient = steamClient;
        this.steamExecutor = steamExecutor;
        this.gamesUtils = gamesUtils;
        this.mapper = mapper;
        this.steamBatchParser = steamBatchParser;
    }


    @Override
    public List<GameAutocompleteDTO> autocomplete(String query) {

        log.info("GamesServiceImpl - autocomplete called for query: {}", query);

        List<GamesInfo> allGames = steamClient.loadAllGames();
        String normalized = normalizeQuery(query);

        // ================= LOCAL SEARCH =================
        long localStart = System.currentTimeMillis();

        List<GamesInfo> localCandidates = new ArrayList<>();
        localCandidates.addAll(gamesUtils.literalSearch(allGames, normalized));
        localCandidates.addAll(gamesUtils.prefixFallback(allGames, normalized));
        localCandidates.addAll(gamesUtils.numericFallback(allGames, normalized));
        localCandidates.addAll(gamesUtils.fuzzySearch(allGames, normalized));

        log.info("GamesServiceImpl - autocomplete local search took {} ms",
                System.currentTimeMillis() - localStart);

        List<GameAutocompleteDTO> remoteCandidates = new ArrayList<>();

        if (normalized.length() >= 3) {

            long steamStart = System.currentTimeMillis();

            CompletableFuture<List<SteamStoreDTO>> storeFuture =
                    CompletableFuture.supplyAsync(
                            () -> steamClient.searchSteamStore(normalized),
                            steamExecutor
                    );

            List<GameAutocompleteDTO> storeResults = storeFuture.join()
                    .stream()
                    .map(mapper::toGameAutocompleteDTOFromSteamStore)
                    .toList();

            log.info("GamesServiceImpl - autocomplete steam search took {} ms",
                    System.currentTimeMillis() - steamStart);
            remoteCandidates.addAll(storeResults);
        }

        log.info(
                "GamesServiceImpl - autocomplete retrieved {} local candidates and {} remote candidates",
                localCandidates.size(),
                remoteCandidates.size()
        );
        log.warn("Milliseconds taken for autocomplete: {} ms", System.currentTimeMillis() - localStart);
        return Stream.concat(
                localCandidates.stream().map(mapper::toGameAutocompleteDTO),
                remoteCandidates.stream()
        ).distinct().limit(10).toList();
    }

    @Override
    public PagedResponse<GameListDTO> listGames(@NotNull List<GameAutocompleteDTO> searchedGame, int page, int size) {

        /*log.info("GamesServiceImpl - listGames called for query: {}", searchedGame);
        List<GameListDTO> gameListDTOs = steamClient.getGameDetailsBatch(searchedGame);

        log.info("GamesServiceImpl - listGames retrieved {} games", gameListDTOs.size());
        List<GameListDTO> gameList = gameListDTOs.stream()
                .skip((long) page * size)   // pagination: skip to the correct elements
                .limit(size)
                .toList();

        return new PagedResponse<>(
                gameList,
                page,
                size,
                gameListDTOs.size(),
                (int) Math.ceil((double) gameListDTOs.size() / size)
        );*/
        return null;
    }

    @Override
    public PagedResponse<GameListDTO> getGames(String query, int page, int size) {

        log.info("GamesServiceImpl - searchGames called for gameName: {}", query);

        List<GamesInfo> allGames = steamClient.loadAllGames();

        List<GameListDTO> localResult = steamClient.searchGame(allGames, query);

        // filter to only valid IDs (those that exist in the full games list)
        Set<Integer> validIds = allGames.stream()
                .map(GamesInfo::id)
                .collect(Collectors.toSet());

        List<Integer> ids = localResult.stream()
                .map(GameListDTO::id)
                .filter(validIds::contains)
                .toList();

        if (localResult.isEmpty()) {

            return new PagedResponse<>(
                    Collections.emptyList(),
                    page,
                    size,
                    0,
                    0
            );
        }

        List<GameListDTO> fullDetails = steamClient.getGameDetailsBatch(ids);

        int total = fullDetails.size();
        int from = page * size;
        int to = Math.min(from + size, total);
        List<GameListDTO> pageContent =
                from >= total ? List.of() : fullDetails.subList(from, to);

        return new PagedResponse<>(
                pageContent,
                page,
                size,
                total,
                (int) Math.ceil((double) total / size)
        );

    }

    @Override
    public GameSheetDTO gameSheet(Integer gameId) {

       /* log.info("GamesServiceImpl - gameSheet called for gameId: {}", gameId);
        log.info("GamesServiceImpl - gameSheet retrieved details for gameId: {}", gameId);
        return steamClient.getGameSheetInformation(gameId);*/
        return null;
    }
}