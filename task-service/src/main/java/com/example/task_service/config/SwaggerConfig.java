package com.example.task_service.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
  @Value("${swaggerdoc.title}")
  private String title;
  @Value("${swaggerdoc.desc}")
  private String description;
  @Value("${swaggerdoc.version}")
  private String version;

  @Bean
  public OpenAPI customOpenAPI(){
    return new OpenAPI()
      .components(new Components()
        .addSecuritySchemes("bearerAuth", 
          new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
          ))
      .security(List.of(new SecurityRequirement().addList("bearerAuth")))
      .info(new Info()
        .title(title)
        .description(description)
        .version(version)
      );
  }
}
