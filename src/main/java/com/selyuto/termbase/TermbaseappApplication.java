package com.selyuto.termbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAutoConfiguration
@ComponentScan
@EnableScheduling
public class TermbaseappApplication {

    public static void main(String[] args) {
        SpringApplication.run(TermbaseappApplication.class, args);
    }

}
