package com.selyuto.termbase;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableScheduling
public class TermbaseappApplication {

    @Value("${api.hostname}")
    private String hostname;

    public static void main(String[] args) {
        SpringApplication.run(TermbaseappApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowedOrigins(hostname)
                        .allowedHeaders("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization", "password", "email", "Set-Cookie")
                        .allowedMethods("GET", "POST", "OPTIONS", "DELETE", "PUT")
                        .allowCredentials(true);
            }
        };
    }
}
