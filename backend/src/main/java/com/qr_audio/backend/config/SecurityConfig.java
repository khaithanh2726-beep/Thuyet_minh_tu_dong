package com.qr_audio.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // Phân quyền truy cập API
            .authorizeHttpRequests(auth -> auth
                // Nhóm 1: Dành cho Swagger UI để test API
                .requestMatchers("/v3/api-docs","/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                
                // Nhóm 2: Dành cho Khách du lịch quét mã QR
                .requestMatchers("/api/qr/**", "/api/auth/**").permitAll()
                
                // Nhóm 3: Dành cho Admin và người dùng có quyền
                .anyRequest().authenticated()
            );
            
        return http.build();
    }
}