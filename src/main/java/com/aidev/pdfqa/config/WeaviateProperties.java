package com.aidev.pdfqa.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.vectorstore.weaviate")
public class WeaviateProperties {
    private String scheme;
    private String host;
    private int port;
    private String objectClass;
}