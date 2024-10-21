package com.sarabarbara.manager.dto.users;

import lombok.*;

/**
 * SearchUserDTO class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 18/10/2024
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchDTO {

    /**
     * The username
     */

    private String username;

    /**
     * The profilePictureURL
     */

    private String profilePictureURL;

}
