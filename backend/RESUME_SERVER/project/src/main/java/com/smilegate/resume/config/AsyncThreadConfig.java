package com.smilegate.resume.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncThreadConfig {

    @Bean
    public Executor asyncThreadTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolExecutor = new ThreadPoolTaskExecutor();
        threadPoolExecutor.setCorePoolSize(15);
        threadPoolExecutor.setMaxPoolSize(15);
        threadPoolExecutor.setQueueCapacity(1000);
        threadPoolExecutor.setThreadNamePrefix("resume-thread-pool-");
        return threadPoolExecutor;
    }

}
