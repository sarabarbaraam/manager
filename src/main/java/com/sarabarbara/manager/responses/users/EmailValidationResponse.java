package com.sarabarbara.manager.responses.users;


/**
 * EmailValidationResponse class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 01/01/2026
 */

public record EmailValidationResponse(

        String status,
        String subStatus
) {
}
