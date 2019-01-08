package com.tdpteam.web;

import com.tdpteam.repo.config.RepoConfig;
import com.tdpteam.service.config.ServiceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackageClasses = ServiceConfig.class)
@EntityScan(basePackageClasses = RepoConfig.class)
@EnableJpaRepositories(basePackageClasses = RepoConfig.class)
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}

