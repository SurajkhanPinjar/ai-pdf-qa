package com.aidev.pdfqa.config;

import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocumentParserConfig {

    @Bean
    public DocumentSplitter documentSplitter() {
        // recursive chunking → best for RAG
        return DocumentSplitters.recursive(500, 50);
    }

}

