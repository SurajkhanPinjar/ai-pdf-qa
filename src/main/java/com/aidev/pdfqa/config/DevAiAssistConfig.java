//package com.aidev.pdfqa.config;
//
//import dev.langchain4j.model.embedding.EmbeddingModel;
//import dev.langchain4j.model.ollama.OllamaChatModel;
//import dev.langchain4j.rag.content.retriever.ContentRetriever;
//import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
//import dev.langchain4j.service.AiServices;
//import dev.langchain4j.store.embedding.weaviate.WeaviateEmbeddingStore;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class DevAiAssistConfig {
//
//    // ------------------- WEAVIATE STORES -------------------
//
//    @Bean
//    @Qualifier("javaDocsStore")
//    public WeaviateEmbeddingStore javaDocsStore() {
//        return WeaviateEmbeddingStore.builder()
//                .scheme("http")
//                .host("localhost")
//                .port(8080)
//                .objectClass("JavaDocs")
//                .avoidDups(true)
//                .build();
//    }
//    // ------------------- CONTENT RETRIEVERS -------------------
//
//    @Bean
//    @Qualifier("javaDocsRetriever")
//    public ContentRetriever javaDocsRetriever(
//            @Qualifier("javaDocsStore") WeaviateEmbeddingStore store,
//            EmbeddingModel embeddingModel) {
//        return new EmbeddingStoreContentRetriever(store, embeddingModel);
//    }
//
//    // ------------------- AI SERVICES -------------------
//
//    @Bean
//    public JavaDocsService javaDocsRag(OllamaChatModel chatModel,
//                                       @Qualifier("javaDocsRetriever") ContentRetriever retriever) {
//        return AiServices.builder(JavaDocsService.class)
//                .chatLanguageModel(chatModel)
//                .contentRetriever(retriever)
//                .build();
//    }
//}