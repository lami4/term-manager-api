package com.selyuto.termbase.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Value("${api.hostname}")
    private String hostname;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOrigins(hostname)
                .allowedHeaders("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization", "password", "email", "Set-Cookie")
                .allowedMethods("GET", "POST", "OPTIONS", "DELETE", "PUT")
                .allowCredentials(true);
    }
}
