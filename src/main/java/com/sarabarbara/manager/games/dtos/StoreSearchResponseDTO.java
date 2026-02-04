package com.sarabarbara.manager.games.dtos;


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

        boolean success,
        StoreSearchData data,
        String message

) {
}
