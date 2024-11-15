package com.sarabarbara.manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * AppConfig class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 1/11/2024
 */

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public StringBuilder stringBuilder() {
        return new StringBuilder();
    }

}
