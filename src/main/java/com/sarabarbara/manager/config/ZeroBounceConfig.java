package com.sarabarbara.manager.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ZeroBounceProperties class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 01/11/2024
 */

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "zerobounce")
public class ZeroBounceConfig {

    /**
     * The apiKey
     */

    private String apiKey;

}
