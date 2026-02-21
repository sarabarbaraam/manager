package com.sarabarbara.manager.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * AsyncConfig class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 05/02/2026
 */

@Configuration
public class AsyncConfig {

    @Bean(name = "steamDetailsExecutor")
    public Executor steamDetailsExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("steam-details-"); // name threads for debugging

        executor.initialize();
        return executor;
    }
}
