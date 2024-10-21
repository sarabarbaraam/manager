package com.sarabarbara.manager.dto;

import com.sarabarbara.manager.dto.users.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * UpdatedResponse class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 06/10/2024
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UpdateUserResponse {

    private String message;
    private UserDTO user;

}
