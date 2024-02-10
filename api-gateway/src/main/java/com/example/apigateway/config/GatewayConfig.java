package com.example.apigateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.apigateway.filter.AuthFilter;

@Configuration
public class GatewayConfig {
  
  @Autowired
  AuthFilter filter;

  @Bean
  public RouteLocator routes(RouteLocatorBuilder builder) {
    return builder.routes()
      .route("login-service", r -> r.path("/api/v1/auth/**")
            .filters(f -> f.filter(filter))
            .uri("http://localhost:30201"))
      .route("task-service", r -> r.path("/api/v1/tasks/**")
            .filters(f -> f.filter(filter))
            .uri("http://localhost:30202"))
      .build();
  }
}
