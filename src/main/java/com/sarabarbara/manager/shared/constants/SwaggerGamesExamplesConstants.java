package com.sarabarbara.manager.shared.constants;


/**
 * SwaggerGamesExamplesConstants class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 13/01/2026
 */

public class SwaggerGamesExamplesConstants {

    private SwaggerGamesExamplesConstants() {
    }

    // ===== SEARCH GAMES =====

    public static final String SEARCH_GAME_SUCCESS_RESPONSE = """
            {
              "data": [
                {
                  "steam_appid": 220,
                  "name": "Half-Life 2",
                  "capsuleImage": "https://cdn.akamai.steamstatic.com/steam/apps/220/capsule_184x69.jpg"
                },
                {
                  "steam_appid": 400,
                  "name": "Portal",
                  "capsuleImage": "https://cdn.akamai.steamstatic.com/steam/apps/400/capsule_184x69.jpg"
                },
                {
                  "steam_appid": 620,
                  "name": "Portal 2",
                  "capsuleImage": "https://cdn.akamai.steamstatic.com/steam/apps/620/capsule_184x69.jpg"
                },
                {
                  "steam_appid": 570,
                  "name": "Dota 2",
                  "capsuleImage": "https://cdn.akamai.steamstatic.com/steam/apps/570/capsule_184x69.jpg"
                }
              ],
              "page": 1,
                "size": 4,
                "totalElements": 12,
                "totalPages": 3,
              "message": "Successful."
            }
            """;

    public static final String SEARCH_GAME_BAD_REQUEST_RESPONSE = """
            {
              "id": null,
              "data": null,
              "page": 0,
              "size": 0,
              "totalElements": 0,
              "totalPages": 0,
              "message": "The game name must not be empty or null."
            }
            """;

    public static final String SEARCH_GAME_NOT_FOUND_RESPONSE = """
            {
              "id": null,
              "data": [],
              "page": 1,
              "size": 0,
              "totalElements": 0,
              "totalPages": 0,
              "message": "No games found for the given name."
            }
            """;

    public static final String SEARCH_GAME_INTERNAL_ERROR_RESPONSE = """
            {
              "id": null,
              "data": null,
              "page": 0,
              "size": 0,
              "totalElements": 0,
              "totalPages": 0,
              "message": "Some internal error occurred."
            }
            """;

    // ===== LIST GAMES =====

    public static final String LIST_GAME_SUCCESS_RESPONSE = """
            {
              "data": [
                {
                  "id": 570660,
                  "name": "The Binding of Isaac: Afterbirth+",
                  "capsuleImage": "https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/570660/capsule_231x87.jpg?t=1573195823",
                  "shortDescription": "When Isaac’s mother starts hearing the voice of God demanding a sacrifice be made to prove her faith, Isaac escapes into the basement facing droves of deranged enemies, lost brothers and sisters, his fears, and eventually his mother. Gameplay The Binding of Isaac is a randomly generated action RPG shooter with heavy Rogue-like elements."
                }
              ],
              "totalResults": 1,
              "currentPage": 1,
              "totalPage": 1,
              "message": "Successful."
            }
            """;

    public static final String LIST_GAME_BAD_REQUEST_RESPONSE = """
            {
              "id": null,
              "data": null,
              "totalResults": 0,
              "currentPage": 0,
              "totalPage": 0,
              "message": "The game name must not be empty or null."
            }
            """;

    public static final String LIST_GAME_NOT_FOUND_RESPONSE = """
            {
              "id": 570660,
              "data": [],
              "totalResults": 0,
              "currentPage": 1,
              "totalPage": 0,
              "message": "No games found for the given name."
            }
            """;

    public static final String LIST_GAME_INTERNAL_ERROR_RESPONSE = """
            {
              "data": null,
              "totalResults": 0,
              "currentPage": 0,
              "totalPage": 0,
              "message": "Some internal error occurred."
            }
            """;

    // ===== GAME SHEET =====

    public static final String GAME_SHEET_SUCCESS_RESPONSE = """
            {
              "steam_appid": 413150,
              "type": "game",
              "name": "Stardew Valley",
              "is_free": false,
              "controller_support": "full",
              "dlc": [440820],
              "short_description": "You've inherited your grandfather's old farm plot in Stardew Valley. Armed with hand‑me‑down tools and a few coins, you set out to begin your new life.",
              "detailed_description": "Stardew Valley is an open‑ended country‑life RPG where you restore your grandfather's farm, build relationships, explore caves, and grow your homestead.",
              "about_the_game": "Stardew Valley is an open‑ended country‑life RPG full of farming, crafting, exploration, and community building.",
              "supported_languages": "English, Spanish, French, German, Japanese",
              "header_image": "https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/413150/header.jpg",
              "capsule_image": "https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/413150/capsule_231x87.jpg",
              "capsule_imagev5": "https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/413150/capsule_184x69.jpg",
              "website": "http://www.stardewvalley.net",
            
              "pc_requirements": {
                "minimum": "OS: Windows Vista or greater\\nProcessor: 2 GHz\\nMemory: 2 GB RAM\\nGraphics: 256 MB video memory",
                "recommended": "OS: Windows 10\\nProcessor: 3 GHz\\nMemory: 4 GB RAM"
              },
            
              "mac_requirements": {
                "minimum": "OS: Mac OSX 10.10+\\nProcessor: 2 GHz\\nMemory: 2 GB RAM"
              },
            
              "linux_requirements": {
                "minimum": "OS: Ubuntu 12.04+\\nProcessor: 2 GHz\\nMemory: 2 GB RAM"
              },
            
              "legal_notice": "© ConcernedApe LLC",
              "developers": ["ConcernedApe"],
              "publishers": ["ConcernedApe"],
            
              "price_overview": {
                "currency": "EUR",
                "initial": 1499,
                "final": 1499,
                "discount_percent": 0
              },
            
              "platforms": {
                "windows": true,
                "mac": true,
                "linux": true
              },
            
              "metacritic": {
                "score": 89,
                "url": "https://www.metacritic.com/game/pc/stardew-valley"
              },
            
              "categories": [
                { "id": 1, "description": "Single-player" },
                { "id": 2, "description": "Steam Achievements" }
              ],
            
              "genres": [
                { "id": "12", "description": "RPG" },
                { "id": "28", "description": "Simulation" }
              ],
            
              "screenshots": [
                {
                  "id": 1,
                  "path_thumbnail": "https://cdn.akamai.steamstatic.com/steam/apps/413150/ss_1_thumb.jpg",
                  "path_full": "https://cdn.akamai.steamstatic.com/steam/apps/413150/ss_1_full.jpg"
                },
                {
                  "id": 2,
                  "path_thumbnail": "https://cdn.akamai.steamstatic.com/steam/apps/413150/ss_2_thumb.jpg",
                  "path_full": "https://cdn.akamai.steamstatic.com/steam/apps/413150/ss_2_full.jpg"
                }
              ],
            
              "movies": [
                {
                  "id": 1001,
                  "name": "Launch Trailer",
                  "thumbnail": "https://cdn.akamai.steamstatic.com/steam/apps/413150/movie_thumb.jpg",
                  "webm": { "480": "https://cdn.akamai.steamstatic.com/steam/apps/413150/trailer_480.webm" },
                  "mp4": { "480": "https://cdn.akamai.steamstatic.com/steam/apps/413150/trailer_480.mp4" }
                }
              ],
            
              "achievements": {
                "total": 49,
            
                "highlighted": [
                  {
                    "name": "Greenhorn",
                    "description": "Earn 15,000g",
                    "icon": "https://cdn.akamai.steamstatic.com/steamcommunity/public/images/apps/413150/achv_greenhorn.jpg",
                    "icon_gray": "https://cdn.akamai.steamstatic.com/steamcommunity/public/images/apps/413150/achv_greenhorn_gray.jpg"
                  },
                  {
                    "name": "Cowpoke",
                    "description": "Earn 50,000g",
                    "icon": "https://cdn.akamai.steamstatic.com/steamcommunity/public/images/apps/413150/achv_cowpoke.jpg",
                    "icon_gray": "https://cdn.akamai.steamstatic.com/steamcommunity/public/images/apps/413150/achv_cowpoke_gray.jpg"
                  },
                  {
                    "name": "Homesteader",
                    "description": "Earn 250,000g",
                    "icon": "https://cdn.akamai.steamstatic.com/steamcommunity/public/images/apps/413150/achv_homesteader.jpg",
                    "icon_gray": "https://cdn.akamai.steamstatic.com/steamcommunity/public/images/apps/413150/achv_homesteader_gray.jpg"
                  },
                  {
                    "name": "Millionaire",
                    "description": "Earn 1,000,000g",
                    "icon": "https://cdn.akamai.steamstatic.com/steamcommunity/public/images/apps/413150/achv_millionaire.jpg",
                    "icon_gray": "https://cdn.akamai.steamstatic.com/steamcommunity/public/images/apps/413150/achv_millionaire_gray.jpg"
                  },
                  {
                    "name": "Legend",
                    "description": "Catch the legendary fish",
                    "icon": "https://cdn.akamai.steamstatic.com/steamcommunity/public/images/apps/413150/achv_legend.jpg",
                    "icon_gray": "https://cdn.akamai.steamstatic.com/steamcommunity/public/images/apps/413150/achv_legend_gray.jpg"
                  }
                ],
            
                "achievements": [
                  {
                    "name": "Greenhorn",
                    "description": "Earn 15,000g",
                    "icon": "https://cdn.akamai.steamstatic.com/steamcommunity/public/images/apps/413150/achv_greenhorn.jpg",
                    "icon_gray": "https://cdn.akamai.steamstatic.com/steamcommunity/public/images/apps/413150/achv_greenhorn_gray.jpg"
                  },
                  {
                    "name": "Cowpoke",
                    "description": "Earn 50,000g",
                    "icon": "https://cdn.akamai.steamstatic.com/steamcommunity/public/images/apps/413150/achv_cowpoke.jpg",
                    "icon_gray": "https://cdn.akamai.steamstatic.com/steamcommunity/public/images/apps/413150/achv_cowpoke_gray.jpg"
                  },
                  {
                    "name": "Homesteader",
                    "description": "Earn 250,000g",
                    "icon": "https://cdn.akamai.steamstatic.com/steamcommunity/public/images/apps/413150/achv_homesteader.jpg",
                    "icon_gray": "https://cdn.akamai.steamstatic.com/steamcommunity/public/images/apps/413150/achv_homesteader_gray.jpg"
                  }
                ]
              },
            
              "query_summary": {
                "total_reviews": 500000,
                "positive": 480000,
                "negative": 20000,
                "score": 96
              },
            
              "release_date": {
                "coming_soon": false,
                "date": "26 Feb, 2016"
              },
            
              "ratings": {
                "esrb": "Everyone 10+",
                "pegi": "7"
              }
            }
            """;

    public static final String GAME_SHEET_BAD_REQUEST_RESPONSE = """
            {
              "id": null,
              "data": null,
              "message": "The game ID must be a positive integer."
            }
            """;

    public static final String GAME_SHEET_NOT_FOUND_RESPONSE = """
            {
              "id": null,
              "data": null,
              "message": "No game found for the given ID."
            }
            """;

    public static final String GAME_SHEET_INTERNAL_ERROR_RESPONSE = """
            {
              "id": null,
              "data": null,
              "message": "Some internal error occurred."
            }
            """;
}