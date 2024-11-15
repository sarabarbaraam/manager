package com.sarabarbara.manager.dto.users;

import com.sarabarbara.manager.models.Genre;
import lombok.*;

import java.util.Objects;

/**
 * UserCreateDTO class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 30/10/2024
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserCreateDTO {

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
        if (!(o instanceof UserCreateDTO that)) return false;
        return Objects.equals(getName(), that.getName()) && Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getEmail(), that.getEmail()) && getGenre() == that.getGenre() && Objects.equals(getProfilePictureURL(), that.getProfilePictureURL()) && Objects.equals(getPremium(), that.getPremium());
    }

    /**
     * The hashCode
     */

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getUsername(), getEmail(), getGenre(), getProfilePictureURL(), getPremium());
    }

}
