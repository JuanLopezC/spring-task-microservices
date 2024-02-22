package com.jucalc.spring_login.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

  @Value("${swaggerdoc.title}")
  private String title;
  @Value("${swaggerdoc.desc}")
  private String description;
  @Value("${swaggerdoc.version}")
  private String version;
  
  @Bean
  public OpenAPI customOpenApi(){
    return new OpenAPI()
      .components(new Components())
      .info(new Info()
        .title(title)
        .description(description)
        .version(version)
      );
  }
}
