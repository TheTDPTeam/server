package com.tdpteam.service.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ServiceConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    /*
        An mail content template to send to users when their accounts are created
        @Param 1: First name
        @Param 2: Email
        @Param 3: Generated password
     */
    @Bean(name = "getAccountCreationMailContentTemplate")
    public String getAccountCreationMailContentTemplate(){
        return "Hi %s,\nYour account is created with email: %s\nYour default password is: %s\nBest regards,\nAdmin\n";
    }
}
