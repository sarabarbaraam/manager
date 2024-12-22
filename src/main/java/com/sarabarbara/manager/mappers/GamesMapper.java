package com.sarabarbara.manager.mappers;

import com.sarabarbara.manager.dto.games.GamesSearchDTO;
import com.sarabarbara.manager.dto.games.GamesSheetDTO;
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

    public static List<GamesSearchDTO> toGamesSearchDTOMapper(@NonNull List<Games> searchedGame) {

        return searchedGame.stream()
                .map(game -> GamesSearchDTO.builder()
                        .name(game.getName())
                        .capsuleImage(game.getCapsuleImage())
                        .shortDescription(game.getShortDescription())
                        .build())
                .toList();
    }

    /**
     * The {@link GamesSheetDTO} mapper
     *
     * @param gameName the name of the game
     *
     * @return the GamesSheetDTO
     */

    public static GamesSheetDTO toGamesSheetDTOMapper(@NonNull Games gameName) {

        return GamesSheetDTO.builder()
                .type(gameName.getType())
                .name(gameName.getName())
                .isFree(gameName.isFree())
                .controllerSupport(gameName.getControllerSupport())
                .dlc(gameName.getDlc())
                .aboutTheGame(gameName.getAboutTheGame())
                .supportedLanguages(gameName.getSupportedLanguages())
                .headerImage(gameName.getHeaderImage())
                .capsuleImage(gameName.getCapsuleImage())
                .website(gameName.getWebsite())
                .pcRequirements(gameName.getPcRequirements())
                .macRequirements(gameName.getMacRequirements())
                .linuxRequirements(gameName.getLinuxRequirements())
                .legalNotice(gameName.getLegalNotice())
                .developers(gameName.getDevelopers())
                .publishers(gameName.getPublishers())
                .price(gameName.getPrice())
                .platforms(gameName.getPlatforms())
                .metacritic(gameName.getMetacritic())
                .categories(gameName.getCategories())
                .genres(gameName.getGenres())
                .screenShots(gameName.getScreenShots())
                .movies(gameName.getMovies())
                .achievements(gameName.getAchievements())
                .ratings(gameName.getRatings())
                .releaseDate(gameName.getReleaseDate())
                .ageRatings(gameName.getAgeRatings())
                .build();
    }

}
