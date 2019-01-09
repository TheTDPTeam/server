package com.tdpteam.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.tdpteam.service", "com.tdpteam.web"})
@EntityScan(basePackages = "com.tdpteam.repo.entity")
@EnableJpaRepositories(basePackages = "com.tdpteam.repo.repository")
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}

