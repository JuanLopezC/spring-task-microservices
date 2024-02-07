package com.example.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  
  @Bean
  public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
    return http.csrf(csrf -> csrf.disable()).build();
  }

}