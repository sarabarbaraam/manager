package com.sarabarbara.manager.dto;

import com.sarabarbara.manager.dto.users.UserCreateDTO;
import lombok.*;

import java.util.Objects;

/**
 * CreateResponse class
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
public class CreateResponse {

    /**
     * The success
     */

    private boolean success;

    /**
     * The userDTO
     */

    private UserCreateDTO userCreate;

    /**
     * The message
     */

    private String message;

    /**
     * The equals
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateResponse that)) return false;
        return isSuccess() == that.isSuccess() && Objects.equals(getUserCreate(), that.getUserCreate()) && Objects.equals(getMessage(), that.getMessage());
    }

    /**
     * The hashCode
     */

    @Override
    public int hashCode() {
        return Objects.hash(isSuccess(), getUserCreate(), getMessage());
    }

}
