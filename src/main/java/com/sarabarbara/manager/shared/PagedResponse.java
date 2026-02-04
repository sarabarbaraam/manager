package com.sarabarbara.manager.shared;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

/**
 * PagedResponse class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 14/01/2026
 */

@Builder
@Schema(name = "Paged Response", description = "Response for paginated data")
public record PagedResponse<T>(

        @Schema(description = "Content of the current page")
        List<T> content,

        @Schema(description = "Current page number", example = "1")
        int page,

        @Schema(description = "Number of items per page", example = "10")
        int size,

        @Schema(description = "Total number of elements", example = "100")
        long totalElements,

        @Schema(description = "Total number of pages", example = "10")
        int totalPages
) {
}