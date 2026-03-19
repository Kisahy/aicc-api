package com.kisahy.aicc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AiccApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(AiccApiApplication.class, args);
    }
}
