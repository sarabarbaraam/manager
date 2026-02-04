package com.sarabarbara.manager.shared.constants;


/**
 * SwaggerAuthExamplesConstants class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 12/01/2026
 */

public class SwaggerAuthExamplesConstants {

    private SwaggerAuthExamplesConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String AUTHENTICATION_FAILED_RESPONSE = """
            {
              "timestamp": "2026-01-12T10:15:30.123+00:00",
              "status": 401,
              "error": "Unauthorized",
              "message": "Invalid username or password",
              "path": "/api/v1/auth/login"
            }
            """;

}
