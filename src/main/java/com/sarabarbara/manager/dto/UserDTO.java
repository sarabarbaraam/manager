package com.sarabarbara.manager.dto;

import com.sarabarbara.manager.models.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * UserDTO class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 06/10/2024
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    /**
     * The id
     */

    private Long id;

    /**
     * The name
     */

    private String name;

    /**
     * The username
     */

    private String username;

    /**
     * The password
     */

    private String password;

    /**
     * The email
     */

    private String email;

    /**
     * The genre
     */

    private Genre genre;

    /**
     * The profilePictureURL
     */

    private String profilePictureURL;

    /**
     * The premium
     */
    private boolean premium;

    /**
     * Search user constructor
     */
    public UserDTO(String username, String profilePictureURL) {

        this.username = username;
        this.profilePictureURL = profilePictureURL;

    }

}
