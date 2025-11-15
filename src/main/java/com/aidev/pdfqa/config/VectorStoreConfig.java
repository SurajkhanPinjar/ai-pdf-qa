package com.aidev.pdfqa.config;

import dev.langchain4j.store.embedding.weaviate.WeaviateEmbeddingStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VectorStoreConfig {

    @Bean
    public WeaviateEmbeddingStore weaviateStore(WeaviateProperties props) {

        return WeaviateEmbeddingStore.builder()
                .scheme(props.getScheme())     // http
                .host(props.getHost())         // localhost
                .port(props.getPort())         // 8082
                .objectClass(props.getObjectClass()) // PdfChunk
                .build();
    }
}