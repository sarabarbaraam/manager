package com.sarabarbara.manager.dto;

import lombok.*;

import java.util.Objects;

/**
 * EmailValidationResponse class
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
public class EmailValidationResponse {

    /**
     * The status
     */

    private String status;

    /**
     * The subStatus
     */

    private String subStatus;

    /**
     * The equals
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmailValidationResponse that)) return false;
        return Objects.equals(getStatus(), that.getStatus()) && Objects.equals(getSubStatus(), that.getSubStatus());
    }

    /**
     * The hashCode
     */

    @Override
    public int hashCode() {
        return Objects.hash(getStatus(), getSubStatus());
    }

}
