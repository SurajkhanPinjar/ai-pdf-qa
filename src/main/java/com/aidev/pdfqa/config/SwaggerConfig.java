package com.aidev.pdfqa.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "ai-pdf-qa API",
                version = "1.0",
                description = "This is a ai-pdf-qa Swagger configuration for testing"
        )
)
public class SwaggerConfig {
    // No code needed — swagger auto config works
}