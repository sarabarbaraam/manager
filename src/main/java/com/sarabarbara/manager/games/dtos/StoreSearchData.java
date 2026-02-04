package com.sarabarbara.manager.games.dtos;


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

        List<GameSearchDTO> content,
        int totalElements,
        int totalPages,
        int page,
        int size

) {
}
