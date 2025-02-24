package com.sarabarbara.manager.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sarabarbara.manager.models.games.*;
import com.sarabarbara.manager.services.GamesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * GamesControllerTest class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 05/12/2024
 */

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class GamesControllerTest {

    @InjectMocks
    private GamesController gamesController;

    @Mock
    private GamesService gamesService;

    private MockMvc mockMvc;

    private Games game;

    private final List<Integer> dlc = Collections.singletonList(413150);
    private final List<String> developers = Collections.singletonList("developers");
    private final List<String> publishers = Collections.singletonList("publishers");
    private final List<Categories> categories = List.of(new Categories(2, "single player"));
    private final List<GameGenre> genre = List.of(new GameGenre("1", "action"));
    private final List<Screenshots> screenshots = List.of(new Screenshots("pathThumbnail", "pathFull"));
    private final List<AchievementDetails> achievementDetails = List.of(
            new AchievementDetails("a0", 0, "Greenhorn", 0,
                    "Earn 15,000g", "icon", "iconGray"));

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(gamesController).build();

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
    void searchTotalGameTest() throws Exception {

        when(gamesService.searchGames(anyString(), anyInt(), anyInt())).thenReturn(List.of(game));

        mockMvc.perform(post("/games/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("Stardew Valley")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void searchPartialGameTest() throws Exception {

        Games game2 = Games.builder()
                .id(2)
                .type("game")
                .name("Stardew Valley 2")
                .capsuleImage("capsuleImage")
                .shortDescription("10/10")
                .build();

        List<Games> searchedGames = List.of(game, game2);
        when(gamesService.searchGames(anyString(), anyInt(), anyInt())).thenReturn(searchedGames);

        mockMvc.perform(post("/games/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("Stardew")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void searchZeroGameNoTest() throws Exception {

        when(gamesService.searchGames(anyString(), anyInt(), anyInt())).thenReturn(List.of());

        mockMvc.perform(post("/games/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("Stardew")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isNoContent());
    }

    @Test
    void searchGameFailTest() throws Exception {

        when(gamesService.searchGames(anyString(), anyInt(), anyInt()))
                .thenThrow(new RuntimeException("Can't search game: Some internal error ocurred."));

        mockMvc.perform(post("/games/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("Stardew")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void gameSheetTest() throws Exception {

        Integer gameId = game.getId();
        when(gamesService.gameSheet(anyInt())).thenReturn(game);

        mockMvc.perform(get("/games/{gameId}", gameId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
