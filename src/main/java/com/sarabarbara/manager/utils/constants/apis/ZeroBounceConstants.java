package com.sarabarbara.manager.utils.constants.apis;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * ZeroBounceConstants class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 30/12/2025
 */

@Schema(name = "ZeroBounce Constants", description = "Constants used for ZeroBounce API integration")
public class ZeroBounceConstants {

    private ZeroBounceConstants() {
    }

    public static final String ZERO_BOUNCE_URL = "https://api.zerobounce.net/v2/validate";

}
