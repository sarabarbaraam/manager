package com.sarabarbara.manager.dto.users;

import com.sarabarbara.manager.enums.UserGenre;
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
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL) // prevent null fields from being processed in JSON conversion
public class UserDTO {

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
     * The userGenre
     */

    private UserGenre userGenre;

    /**
     * The profilePictureURL
     */

    private String profilePictureURL;

    /**
     * The premium
     */

    private Boolean premium;

    /**
     * The equals
     *
     * @param o the o
     *
     * @return the equals
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO userDTO)) return false;
        return Objects.equals(getName(), userDTO.getName()) && Objects.equals(getUsername(), userDTO.getUsername())
                && Objects.equals(getPassword(), userDTO.getPassword()) && Objects.equals(getEmail(),
                userDTO.getEmail()) && getUserGenre() == userDTO.getUserGenre() && Objects.equals(getProfilePictureURL(),
                userDTO.getProfilePictureURL());
    }

    /**
     * The hashcode
     *
     * @return the hash
     */

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getUsername(), getPassword(), getEmail(), getUserGenre(),
                getProfilePictureURL());
    }

}
