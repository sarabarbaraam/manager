package com.sarabarbara.manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;

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

    /**
     * The {@link HttpClient} bean
     *
     * @return httpClient
     */

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newHttpClient();
    }

}
