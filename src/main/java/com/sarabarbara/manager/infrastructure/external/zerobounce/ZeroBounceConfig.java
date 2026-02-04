package com.sarabarbara.manager.infrastructure.external.zerobounce;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ZeroBounceConfig class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 01/01/2026
 */

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "zerobounce")
public class ZeroBounceConfig {

    private String apiKey;
}
