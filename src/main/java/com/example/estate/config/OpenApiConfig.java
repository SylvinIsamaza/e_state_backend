package com.example.estate.config;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Real estate", version = "v1", description = "Documentation of Rest api"))
public class OpenApiConfig {
  
}
