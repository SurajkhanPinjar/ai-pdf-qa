package com.aidev.pdfqa.config;

import dev.langchain4j.store.embedding.weaviate.WeaviateEmbeddingStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VectorStoreConfig {

    @Bean
    public WeaviateEmbeddingStore weaviateStore(
            @Value("${spring.ai.vectorstore.weaviate.scheme:http}") String scheme,
            @Value("${spring.ai.vectorstore.weaviate.host:weaviate}") String host,
            @Value("${spring.ai.vectorstore.weaviate.port:8080}") int port
    ) {
        return WeaviateEmbeddingStore.builder()
                .scheme(scheme)
                .host(host)
                .port(port)
                .objectClass("PdfChunk")
                .build();
    }
}