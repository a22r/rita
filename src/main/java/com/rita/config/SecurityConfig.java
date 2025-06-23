package com.rita.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {  
    @Bean
    public PasswordEncoder passworldEncoder() {
        return new BCryptPasswordEncoder();
    }
}
