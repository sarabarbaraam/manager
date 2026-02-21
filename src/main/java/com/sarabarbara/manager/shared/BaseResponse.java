package com.sarabarbara.manager.shared;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * BaseResponse class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 30/12/2025
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class BaseResponse<T> {

    @Schema(description = "Indicates if the request was successful", example = "true")
    private boolean success;

    @Schema(description = "The data of the response", example = "data")
    private T data;

    @Schema(description = "The message of the response", example = "The request was successful")
    private String message;

}
