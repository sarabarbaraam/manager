package com.sarabarbara.manager.constants;

/**
 * SteamConstants class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 20/11/2024
 */

public class SteamConstants {

    /**
     * The private constructor
     */

    private SteamConstants() {

    }

    /**
     * The GET_GAMES_LIST constant
     */
    public static final String GET_GAMES_LIST = "https://api.steampowered.com/ISteamApps/GetAppList/v2/";

    /**
     * The GET_GAME_DETAIL constant
     */

    public static final String GET_GAME_DETAIL = "https://store.steampowered.com/api/appdetails?appids=";

    /**
     * The GET_GAME_ACHIEVEMENT constant
     * <p>
     * apiKey, appid
     */

    public static final String GET_GAME_ACHIEVEMENT =
            "https://api.steampowered.com/ISteamUserStats/GetSchemaForGame/v2/";

}
