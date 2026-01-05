package com.sarabarbara.manager.shared;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * ErrorResponse class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 30/12/2025
 */

@Getter
@AllArgsConstructor
@Schema(name = "Error Response", description = "Response for error handling")
public class ErrorResponse {

    @Schema(description = "Indicates if the operation was successful", example = "false")
    private boolean success;

    @Schema(description = "The error message", example = "User not found")
    private String message;

    @Schema(description = "The error code", example = "404")
    private int errorCode;

    @Schema(description = "The timestamp of the error", example = "2025-12-30T14:30:00")
    private LocalDateTime timestamp;

}
