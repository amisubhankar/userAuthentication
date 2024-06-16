package com.eshop.userauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationConfig {

    @Bean
    public BCryptPasswordEncoder generateBCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
