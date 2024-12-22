package com.sarabarbara.manager.dto.users;

import lombok.*;

import java.util.Objects;

/**
 * UserDTO class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 21/10/2024
 */

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {

    /**
     * The username
     */

    private String username;

    /**
     * The email
     */

    private String email;

    /**
     * The password
     */

    private String password;

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
        if (!(o instanceof UserLoginDTO that)) return false;
        return Objects.equals(getUsername(), that.getUsername())
                && Objects.equals(getPassword(), that.getPassword())
                && Objects.equals(getEmail(), that.getEmail());
    }

    /**
     * The hashcode
     *
     * @return the hash
     */

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword(), getEmail());
    }

}
