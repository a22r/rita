package com.rita.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig {  
    @Bean
    public PasswordEncoder passworldEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> {}) // 开启CORS，配合下面的CORS配置bean一起用
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // 允许所有 OPTIONS（预检）请求
                .requestMatchers("/api/user/register", "/api/user/login", "/api/user/check", "/api/user/logout").permitAll() // 允许你所有未登录可访问接口
                .anyRequest().authenticated()
            );
        return http.build();
    }
}
