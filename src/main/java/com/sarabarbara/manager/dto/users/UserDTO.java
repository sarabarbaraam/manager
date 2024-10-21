package com.sarabarbara.manager.dto.users;

import com.sarabarbara.manager.models.Genre;
import lombok.*;

import java.util.Objects;

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
@NoArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL) // prevent null fields from being processed in JSON conversion
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
    private Boolean premium;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO userDTO)) return false;
        return Objects.equals(getName(), userDTO.getName()) && Objects.equals(getUsername(), userDTO.getUsername())
                && Objects.equals(getPassword(), userDTO.getPassword()) && Objects.equals(getEmail(),
                userDTO.getEmail()) && getGenre() == userDTO.getGenre() && Objects.equals(getProfilePictureURL(),
                userDTO.getProfilePictureURL());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getUsername(), getPassword(), getEmail(), getGenre(),
                getProfilePictureURL());
    }

}
