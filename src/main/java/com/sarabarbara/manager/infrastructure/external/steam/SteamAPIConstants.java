package com.sarabarbara.manager.infrastructure.external.steam;


/**
 * SteamAPIConstants class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 13/01/2026
 */

public class SteamAPIConstants {

    private SteamAPIConstants() {
        throw new IllegalStateException("Utility class");
    }

    // ======== Steam API Endpoints ========

    public static final String GET_ALL_GAMES = "https://api.steampowered.com/IStoreService/GetAppList/v1/?key=%s&include_games=1";
    public static final String GET_GAMES_STEAM_STORE = "https://store.steampowered.com/api/storesearch/?term=";
    public static final String GET_GAMES_STEAM_DB = "https://steamdb.info/api/SteamRailgun/?q=";
    public static final String GET_GAME = "https://store.steampowered.com/search/results/?query&term=";
    public static final String GET_GAME_DETAIL = "https://store.steampowered.com/api/appdetails?appids=";
    public static final String GET_GAME_ACHIEVEMENT = "https://api.steampowered.com/ISteamUserStats/GetSchemaForGame/v2/";
    public static final String GET_GAME_RATING = "https://store.steampowered.com/appreviews/";

    public static final String GAME_CAPSULE_URL_1 = "https://cdn.akamai.steamstatic.com/steam/apps/";
    public static final String GAME_CAPSULE_URL_2 = "/capsule_184x69.jpg";

    // ======== Steam API Parameters ========

    public static final String L_ENGLISH = "&l=english";
    public static final String CC_US = "&cc=us";
    public static final String FORMAT_JSON = "&json";

}
