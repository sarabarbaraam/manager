package com.sarabarbara.manager.games.dtos;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

/**
 * StoreSearchData class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 16/01/2026
 */

@Builder
public record StoreSearchData(

        @Schema(description = "List of game search results", example = "[]")
        List<GameAutocompleteDTO> content,

        @Schema(description = "Total number of elements found", example = "100")
        int totalElements,

        @Schema(description = "Total number of pages available", example = "10")
        int totalPages,

        @Schema(description = "Current page number", example = "0")
        int page,

        @Schema(description = "Number of elements per page", example = "10")
        int size

) {
}
