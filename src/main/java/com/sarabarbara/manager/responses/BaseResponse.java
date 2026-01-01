package com.sarabarbara.manager.responses;


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

    private boolean success;
    private T data;
    private String message;

}
