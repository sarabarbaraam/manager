package com.sarabarbara.manager.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sarabarbara.manager.apis.SteamAPI;
import com.sarabarbara.manager.models.games.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * GamesServiceTest class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 05/12/2024
 */

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class GamesServiceTest {

    @InjectMocks
    private GamesService gamesService;

    @Mock
    private SteamAPI steamAPI;

    private Games game;

    private final List<Integer> dlc = Collections.singletonList(413150);
    private final List<String> developers = Collections.singletonList("developers");
    private final List<String> publishers = Collections.singletonList("publishers");
    private final List<Categories> categories = List.of(new Categories(2, "single player"));
    private final List<GameGenre> genre = List.of(new GameGenre("1", "action"));
    private final List<Screenshots> screenshots =
            List.of(new Screenshots("pathThumbnail", "pathFull"));
    private final List<AchievementDetails> achievementDetails = List.of(
            new AchievementDetails("a0", 0, "Greenhorn", 0,
                    "Earn 15,000g", "icon", "iconGray"));

    @BeforeEach
    void setUp() {

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode mockJsonNodeRequirements = objectMapper.createObjectNode();

        mockJsonNodeRequirements.put("minimum", "Windows 10");
        mockJsonNodeRequirements.put("recommended", "Windows 11");

        MovieFormat webMovie = MovieFormat.builder()
                .movie480("http://video.akamai.steamstatic.com/store_trailers/256660296/movie480.webm?t=1454099186")
                .movieMax("http://video.akamai.steamstatic.com/store_trailers/256660296/movie_max.webm?t=1454099186")
                .build();
        Movies movie = new Movies(1, "Trailer", "pathThumbnail", webMovie);
        List<Movies> movies = List.of(movie);

        game = Games.builder()
                .id(1)
                .type("game")
                .name("Stardew Valley")
                .isFree(false)
                .controllerSupport("full")
                .dlc(dlc)
                .detailedDescription("300h lle meto entre pecho y espalda si me dejas")
                .aboutTheGame("super juegazo chaval")
                .shortDescription("10/10")
                .supportedLanguages("Ingispitinglis, español y no sé cuáles más")
                .headerImage("headeImage")
                .capsuleImage("capsuleImage")
                .capsuleImageV5("capsuleImagev5")
                .website("website")
                .pcRequirements(mockJsonNodeRequirements)
                .macRequirements(mockJsonNodeRequirements)
                .linuxRequirements(mockJsonNodeRequirements)
                .legalNotice("legalNotice")
                .developers(developers)
                .publishers(publishers)
                .price(PriceOverview.builder()
                        .currency("EUR")
                        .initial(1399)
                        .finalPrice(1399)
                        .discountPercent(0)
                        .initialFormatted("")
                        .finalFormatted("13,99")
                        .build())
                .platforms(Platforms.builder()
                        .windows(true)
                        .mac(true)
                        .linux(true)
                        .build())
                .metacritic(Metacritic.builder()
                        .score(89)
                        .url("urlMetacritic")
                        .build())
                .categories(categories)
                .genres(genre)
                .screenShots(screenshots)
                .movies(movies)
                .achievements(Achievement.builder()
                        .total(49)
                        .achievements(achievementDetails)
                        .build())
                .ratings(Ratings.builder()
                        .reviewScore(9)
                        .reviewScoreDescription("Overwhelmingly Positive")
                        .totalPositive(1123)
                        .totalNegative(12)
                        .totalReviews(21343)
                        .build())
                .releaseDate(ReleaseDate.builder()
                        .comingSoon(false)
                        .date("26 Feb, 2016")
                        .build())
                .ageRatings(AgeRatings.builder()
                        .esrb(AgeRatingDetails.builder()
                                .rating("rating")
                                .descriptors("descriptors")
                                .build())
                        .pegi(AgeRatingDetails.builder()
                                .rating("rating")
                                .descriptors("descriptors")
                                .build())
                        .usk(AgeRatingDetails.builder()
                                .rating("rating")
                                .descriptors("descriptors")
                                .build())
                        .oflc(AgeRatingDetails.builder()
                                .rating("rating")
                                .descriptors("descriptors")
                                .build())
                        .bbfc(AgeRatingDetails.builder()
                                .rating("rating")
                                .descriptors("descriptors")
                                .build())
                        .dejus(AgeRatingDetails.builder()
                                .rating("rating")
                                .descriptors("descriptors")
                                .build())
                        .steamGermany(AgeRatingDetails.builder()
                                .rating("rating")
                                .descriptors("descriptors")
                                .build())
                        .build())
                .build();
    }

    @Test
    void searchTotalGameTest() {

        Map<Integer, String> foundGame = new HashMap<>();
        foundGame.put(game.getId(), game.getName());
        when(steamAPI.getGamesList(anyString())).thenReturn(foundGame);

        List<Games> gamesList = new ArrayList<>();
        gamesList.add(game);
        when(steamAPI.getGame(anyMap())).thenReturn(gamesList);

        List<Games> result = gamesService.searchGames("Stardew Valley", 0, 10);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Stardew Valley");
        assertThat(result.get(0).getCapsuleImage()).isEqualTo("capsuleImage");
        assertThat(result.get(0).getShortDescription()).isEqualTo("10/10");
    }

    @Test
    void searchPartialGameTest() {

        Games game2 = Games.builder()
                .name("Stardew Valley 2")
                .shortDescription("Short description")
                .capsuleImage("capsuleImage")
                .build();

        Map<Integer, String> foundGame = new HashMap<>();
        foundGame.put(game.getId(), game.getName());
        when(steamAPI.getGamesList(anyString())).thenReturn(foundGame);

        List<Games> gamesList = new ArrayList<>();
        gamesList.add(game);
        gamesList.add(game2);
        when(steamAPI.getGame(anyMap())).thenReturn(gamesList);

        List<Games> result = gamesService.searchGames("Stardew", 0, 10);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Stardew Valley");
        assertThat(result.get(0).getCapsuleImage()).isEqualTo("capsuleImage");
        assertThat(result.get(0).getShortDescription()).isEqualTo("10/10");
        assertThat(result.get(1).getName()).isEqualTo("Stardew Valley 2");
        assertThat(result.get(1).getShortDescription()).isEqualTo("Short description");
        assertThat(result.get(1).getCapsuleImage()).isEqualTo("capsuleImage");

    }

    @Test
    void searchZeroGameNoTest() {

        Map<Integer, String> foundGame = new HashMap<>();
        foundGame.put(game.getId(), game.getName());
        when(steamAPI.getGamesList(anyString())).thenReturn(foundGame);

        List<Games> gamesList = new ArrayList<>();
        when(steamAPI.getGame(anyMap())).thenReturn(gamesList);

        List<Games> result = gamesService.searchGames("Stardew", 0, 10);

        assertThat(result).isEmpty();

    }

    @Test
    void gameSheetTest() {

        when(steamAPI.getGameSheet(game.getId())).thenReturn(game);

        Games gameSheet = gamesService.gameSheet(game.getId());

        assertThat(gameSheet.getName()).isEqualTo("Stardew Valley");
    }

}
