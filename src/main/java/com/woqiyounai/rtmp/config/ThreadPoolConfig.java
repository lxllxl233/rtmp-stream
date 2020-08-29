package com.woqiyounai.rtmp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ThreadPoolConfig {

    @Bean
    public ExecutorService getThreadPool(){
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        return executorService;
    }
}
