package com.sarabarbara.manager.dto.users;

import lombok.*;

import java.util.Objects;

/**
 * SearchUserDTO class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 18/10/2024
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserSearchDTO {

    /**
     * The username
     */

    private String username;

    /**
     * The profilePictureURL
     */

    private String profilePictureURL;

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
        if (!(o instanceof UserSearchDTO that)) return false;
        return Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getProfilePictureURL(), that.getProfilePictureURL());
    }

    /**
     * The hashcode
     *
     * @return the hash
     */

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getProfilePictureURL());
    }

}
