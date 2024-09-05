package com.example.demo.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@OpenAPIDefinition(
    info = @io.swagger.v3.oas.annotations.info.Info(
        title = "Demo API",
        version = "v1.0",
        description = "API documentation for demonstrating Swagger configuration"
    ),
    tags = {
        @Tag(name = "product", description = "Operations related to products")
    }
)
public class SwaggerConfig {
    @Bean
    public OpenAPI openapi() {
        return new OpenAPI()
                .info(new Info().title("Demo API").version("v1.0").description("API documentation for demonstrating Swagger configuration"))
                .servers(List.of(new Server().url("http://localhost:8080")));
    }

    @Bean
    public GroupedOpenApi groupOpenApi() {
        return GroupedOpenApi.builder()
                .group("swagger_jpa")
                .packagesToScan("com.example.demo") 
                .build();
    }
    
}
