package com.sarabarbara.manager.config;

import com.sarabarbara.manager.apis.SteamAPI;
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

    /**
     * The {@link RestTemplate}
     *
     * @return RestTemplate
     */

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * The {@link StringBuilder} bean
     *
     * @return StringBuilder
     */

    @Bean
    public StringBuilder stringBuilder() {
        return new StringBuilder();
    }

    @Bean
    public SteamAPI steamAPI(){ return new SteamAPI();}
}
