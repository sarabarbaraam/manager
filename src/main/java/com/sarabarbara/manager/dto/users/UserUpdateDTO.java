package com.sarabarbara.manager.dto.users;

import com.sarabarbara.manager.models.Genre;
import lombok.*;

import java.util.Objects;

/**
 * UserUpdateDTO class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 30/10/2024
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class UserUpdateDTO {

    /**
     * The name
     */

    private String name;

    /**
     * The username
     */

    private String username;

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

    /**
     * The equals
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserUpdateDTO that)) return false;
        return Objects.equals(name, that.name) && Objects.equals(username, that.username) && Objects.equals(email,
                that.email) && genre == that.genre && Objects.equals(profilePictureURL, that.profilePictureURL) && Objects.equals(premium, that.premium);
    }

    /**
     * The hashCode
     */

    @Override
    public int hashCode() {
        return Objects.hash(name, username, email, genre, profilePictureURL, premium);
    }

}
