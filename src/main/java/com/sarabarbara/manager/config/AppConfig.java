package com.sarabarbara.manager.config;


import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * AppConfig class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 30/12/2025
 */

@Configuration
@Schema(name = "App Configuration", description = "Application configuration class")
public class AppConfig {

    /**
     * The {@link BCryptPasswordEncoder} bean
     *
     * @return passwordEncoder
     */

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
