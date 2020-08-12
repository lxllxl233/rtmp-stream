package com.woqiyounai.rtmp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CmdConfig {

    @Bean
    public Runtime getRuntime(){
        Runtime runtime = Runtime.getRuntime();
        return runtime;
    }
}
