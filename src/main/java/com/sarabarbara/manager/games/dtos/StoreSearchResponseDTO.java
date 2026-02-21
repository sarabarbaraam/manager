package com.sarabarbara.manager.games.dtos;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * StoreSearchResponseWrapper class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 16/01/2026
 */

@Builder
public record StoreSearchResponseDTO(

        @Schema(description = "Indicates if the request was successful", example = "true")
        boolean success,

        @Schema(description = "The data of the store search", implementation = StoreSearchData.class)
        StoreSearchData data,

        @Schema(description = "The message of the store search", example = "The request was successful")
        String message

) {
}
