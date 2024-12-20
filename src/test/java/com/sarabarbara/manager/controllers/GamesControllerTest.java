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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
                .id(1L)
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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results[0].name").value("Stardew Valley"))
                .andExpect(jsonPath("$.results[0].capsuleImage").value("capsuleImage"))
                .andExpect(jsonPath("$.results[0].shortDescription").value("10/10"));
    }

    @Test
    void searchPartialGameTest() throws Exception {

        Games game2 = Games.builder()
                .id(2L)
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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results[0].name").value("Stardew Valley"))
                .andExpect(jsonPath("$.results[0].capsuleImage").value("capsuleImage"))
                .andExpect(jsonPath("$.results[0].shortDescription").value("10/10"))
                .andExpect(jsonPath("$.results[1].name").value("Stardew Valley 2"))
                .andExpect(jsonPath("$.results[1].capsuleImage").value("capsuleImage"))
                .andExpect(jsonPath("$.results[1].shortDescription").value("10/10"));
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

        Long gameId = game.getId();
        when(gamesService.gameSheet(anyInt())).thenReturn(game);

        mockMvc.perform(get("/games/{gameId}", gameId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.gameSheet.type").value("game"))
                .andExpect(jsonPath("$.gameSheet.name").value("Stardew Valley"))
                .andExpect(jsonPath("$.gameSheet.is_free").value(false))
                .andExpect(jsonPath("$.gameSheet.controller_support").value("full"))
                .andExpect(jsonPath("$.gameSheet.dlc").value(413150))
                .andExpect(jsonPath("$.gameSheet.about_the_game").value("super juegazo chaval"))
                .andExpect(jsonPath("$.gameSheet.supported_languages")
                        .value("Ingispitinglis, español y no sé cuáles más"))
                .andExpect(jsonPath("$.gameSheet.header_image").value("headeImage"))
                .andExpect(jsonPath("$.gameSheet.capsule_image").value("capsuleImage"))
                .andExpect(jsonPath("$.gameSheet.website").value("website"))
                .andExpect(jsonPath("$.gameSheet.pc_requirements.minimum").value("Windows 10"))
                .andExpect(jsonPath("$.gameSheet.pc_requirements.recommended").value("Windows 11"))
                .andExpect(jsonPath("$.gameSheet.mac_requirements.minimum").value("Windows 10"))
                .andExpect(jsonPath("$.gameSheet.linux_requirements.minimum").value("Windows 10"))
                .andExpect(jsonPath("$.gameSheet.linux_requirements.recommended").value("Windows 11"))
                .andExpect(jsonPath("$.gameSheet.legal_notice").value("legalNotice"))
                .andExpect(jsonPath("$.gameSheet.developers").value("developers"))
                .andExpect(jsonPath("$.gameSheet.publishers").value("publishers"))
                .andExpect(jsonPath("$.gameSheet.price_overview.currency").value("EUR"))
                .andExpect(jsonPath("$.gameSheet.price_overview.initial").value(1399))
                .andExpect(jsonPath("$.gameSheet.price_overview.final").value(1399))
                .andExpect(jsonPath("$.gameSheet.price_overview.discount_percent").value(0))
                .andExpect(jsonPath("$.gameSheet.price_overview.initial_formatted").value(""))
                .andExpect(jsonPath("$.gameSheet.price_overview.final_formatted").value("13,99"))
                .andExpect(jsonPath("$.gameSheet.platforms.windows").value(true))
                .andExpect(jsonPath("$.gameSheet.platforms.mac").value(true))
                .andExpect(jsonPath("$.gameSheet.platforms.linux").value(true))
                .andExpect(jsonPath("$.gameSheet.metacritic.score").value(89))
                .andExpect(jsonPath("$.gameSheet.metacritic.url").value("urlMetacritic"))
                .andExpect(jsonPath("$.gameSheet.categories[0].id").value(2))
                .andExpect(jsonPath("$.gameSheet.categories[0].description")
                        .value("single player"))
                .andExpect(jsonPath("$.gameSheet.genres[0].id").value(1))
                .andExpect(jsonPath("$.gameSheet.genres[0].description").value("action"))
                .andExpect(jsonPath("$.gameSheet.screenshots[0].path_thumbnail")
                        .value("pathThumbnail"))
                .andExpect(jsonPath("$.gameSheet.screenshots[0].path_full")
                        .value("pathFull"))
                .andExpect(jsonPath("$.gameSheet.movies[0].id").value(1))
                .andExpect(jsonPath("$.gameSheet.movies[0].name").value("Trailer"))
                .andExpect(jsonPath("$.gameSheet.movies[0].thumbnail").value("pathThumbnail"))
                .andExpect(jsonPath("$.gameSheet.movies[0].webm.480")
                        .value("http://video.akamai.steamstatic.com/store_trailers/256660296/movie480" +
                                ".webm?t=1454099186"))
                .andExpect(jsonPath("$.gameSheet.movies[0].webm.max")
                        .value("http://video.akamai.steamstatic.com/store_trailers/256660296/movie_max" +
                                ".webm?t=1454099186"))
                .andExpect(jsonPath("$.gameSheet.achievements.total").value(49))
                .andExpect(jsonPath("$.gameSheet.achievements.achievements[0].name").value("a0"))
                .andExpect(jsonPath("$.gameSheet.achievements.achievements[0].defaultvalue")
                        .value(0))
                .andExpect(jsonPath("$.gameSheet.achievements.achievements[0].displayName")
                        .value("Greenhorn"))
                .andExpect(jsonPath("$.gameSheet.achievements.achievements[0].hidden").value(0))
                .andExpect(jsonPath("$.gameSheet.achievements.achievements[0].description")
                        .value("Earn 15,000g"))
                .andExpect(jsonPath("$.gameSheet.achievements.achievements[0].icon").value("icon"))
                .andExpect(jsonPath("$.gameSheet.achievements.achievements[0].icongray")
                        .value("iconGray"))
                .andExpect(jsonPath("$.gameSheet.query_summary.review_score").value(9))
                .andExpect(jsonPath("$.gameSheet.query_summary.review_score_desc")
                        .value("Overwhelmingly Positive"))
                .andExpect(jsonPath("$.gameSheet.query_summary.total_positive").value(1123))
                .andExpect(jsonPath("$.gameSheet.query_summary.total_negative").value(12))
                .andExpect(jsonPath("$.gameSheet.query_summary.total_reviews").value(21343))
                .andExpect(jsonPath("$.gameSheet.release_date.coming_soon").value(false))
                .andExpect(jsonPath("$.gameSheet.release_date.date").value("26 Feb, 2016"))
                .andExpect(jsonPath("$.gameSheet.ratings.esrb.rating").value("rating"))
                .andExpect(jsonPath("$.gameSheet.ratings.esrb.descriptors").value("descriptors"))
                .andExpect(jsonPath("$.gameSheet.ratings.pegi.rating").value("rating"))
                .andExpect(jsonPath("$.gameSheet.ratings.pegi.descriptors").value("descriptors"))
                .andExpect(jsonPath("$.gameSheet.ratings.usk.rating").value("rating"))
                .andExpect(jsonPath("$.gameSheet.ratings.usk.descriptors").value("descriptors"))
                .andExpect(jsonPath("$.gameSheet.ratings.oflc.rating").value("rating"))
                .andExpect(jsonPath("$.gameSheet.ratings.oflc.descriptors").value("descriptors"))
                .andExpect(jsonPath("$.gameSheet.ratings.bbfc.rating").value("rating"))
                .andExpect(jsonPath("$.gameSheet.ratings.bbfc.descriptors").value("descriptors"))
                .andExpect(jsonPath("$.gameSheet.ratings.dejus.rating").value("rating"))
                .andExpect(jsonPath("$.gameSheet.ratings.dejus.descriptors").value("descriptors"))
                .andExpect(jsonPath("$.gameSheet.ratings.steam_germany.rating").value("rating"))
                .andExpect(jsonPath("$.gameSheet.ratings.steam_germany.descriptors")
                        .value("descriptors"));
    }

}
