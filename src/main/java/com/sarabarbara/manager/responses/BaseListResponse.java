package com.sarabarbara.manager.responses;


import lombok.*;

import java.util.List;

/**
 * BaseListResponse class.
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
public class BaseListResponse<T> {

    private boolean success;
    private List<T> data;
    private String message;
}
