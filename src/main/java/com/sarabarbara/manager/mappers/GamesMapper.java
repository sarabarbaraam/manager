package com.sarabarbara.manager.mappers;

import com.sarabarbara.manager.dto.games.GamesSearchDTO;
import com.sarabarbara.manager.models.games.Games;
import lombok.Builder;
import lombok.NonNull;

import java.util.List;

/**
 * GamesMapper class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 20/11/2024
 */

@Builder
public class GamesMapper {

    /**
     * The private constructor
     */

    private GamesMapper() {
    }

    /**
     * The {@link GamesSearchDTO} mapper
     *
     * @param searchedGame the searchedGame
     *
     * @return the GamesSearchDTO
     */
    public static List<GamesSearchDTO> toGamesSearchDTO(@NonNull List<Games> searchedGame) {

        return searchedGame.stream()
                .map(game -> GamesSearchDTO.builder()
                        .name(game.getName())
                        .capsuleImage(game.getCapsuleImage())
                        .shortDescription(game.getShortDescription())
                        .build())
                .toList();
    }

}
