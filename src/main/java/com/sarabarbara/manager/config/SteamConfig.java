package com.sarabarbara.manager.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * SteamConfig class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 20/11/2024
 */

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "steam")
public class SteamConfig {

    /**
     * The apiKey
     */

    private String apiKey;

}
