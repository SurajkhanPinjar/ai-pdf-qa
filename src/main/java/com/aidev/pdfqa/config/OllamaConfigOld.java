//package com.aidev.pdfqa.config;
//
//import dev.langchain4j.model.ollama.OllamaChatModel;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class OllamaConfigOld {
//
//    @Value("${ollama.base-url:http://localhost:11434}")
//    private String baseUrl;
//
//    @Value("${ollama.model:mistral}")
//    private String modelName;
//
//    @Bean
//    public OllamaChatModel ollamaChatModel() {
//        return OllamaChatModel.builder()
//                .baseUrl(baseUrl)
//                .modelName(modelName)
//                .build();
//    }
//}