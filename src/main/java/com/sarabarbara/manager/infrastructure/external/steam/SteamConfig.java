package com.sarabarbara.manager.infrastructure.external.steam;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * SteamConfig class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 13/01/2026
 */

@Data
@Component
@ConfigurationProperties(prefix = "steam")
public class SteamConfig {

    private String apiKey;

    private String capsuleBaseUrl;
}
