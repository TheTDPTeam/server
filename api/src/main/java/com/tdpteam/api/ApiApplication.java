package com.tdpteam.api;

import com.tdpteam.repo.config.RepoConfig;
import com.tdpteam.service.config.ServiceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.tdpteam.service", "com.tdpteam.api"})
@EntityScan(basePackages = "com.tdpteam.repo.entity")
@EnableJpaRepositories(basePackages = "com.tdpteam.repo.repository")
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

}

