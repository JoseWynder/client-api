package com.josewynder.neoapp.clientapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

@Configuration
public class PageableConfig {

    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer customize() {
        return resolver -> {
            resolver.setMaxPageSize(50);
            resolver.setOneIndexedParameters(true);
        };
    }
}

