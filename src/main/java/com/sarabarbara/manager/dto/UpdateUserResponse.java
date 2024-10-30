package com.sarabarbara.manager.dto;

import com.sarabarbara.manager.dto.users.UserUpdateDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

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

    /**
     * The message
     */

    private String message;

    /**
     * The user
     */

    private UserUpdateDTO user;

    /**
     * The equals
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UpdateUserResponse that)) return false;
        return Objects.equals(getMessage(), that.getMessage()) && Objects.equals(getUser(), that.getUser());
    }

    /**
     * The hashCode
     */

    @Override
    public int hashCode() {
        return Objects.hash(getMessage(), getUser());
    }

}
