package com.sarabarbara.manager.dto;

import lombok.*;

import java.util.Objects;

/**
 * LoginResponse class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 21/10/2024
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class LoginResponse {

    /**
     * The success
     */

    private boolean success;

    /**
     * The message
     */

    private String message;

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
        if (!(o instanceof LoginResponse that)) return false;
        return isSuccess() == that.isSuccess() && Objects.equals(getMessage(), that.getMessage());
    }

    /**
     * The hashcode
     *
     * @return the hash
     */

    @Override
    public int hashCode() {
        return Objects.hash(isSuccess(), getMessage());
    }

}
