package com.aidev.pdfqa.config;

//import dev.langchain4j.data.segment.TextSegment;
//import dev.langchain4j.model.embedding.EmbeddingModel;
//import dev.langchain4j.rag.DefaultRetrievalAugmentor;
//import dev.langchain4j.rag.RetrievalAugmentor;
//import dev.langchain4j.rag.content.retriever.ContentRetriever;
//import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
//import dev.langchain4j.store.embedding.EmbeddingStore;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RAGConfig {
//
//    @Bean
//    public ContentRetriever contentRetriever(
//            EmbeddingStore<TextSegment> embeddingStore,
//            EmbeddingModel embeddingModel
//    ) {
//        return EmbeddingStoreContentRetriever.builder()
//                .embeddingStore(embeddingStore)
//                .embeddingModel(embeddingModel)
//                .maxResults(5)
//                .build();
//    }
//
//    @Bean
//    public RetrievalAugmentor retrievalAugmentor(ContentRetriever contentRetriever) {
//        return DefaultRetrievalAugmentor.builder()
//                .contentRetriever(contentRetriever)
//                .build();
//    }
//}


import com.aidev.pdfqa.service.PdfQaService;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.model.embedding.EmbeddingModel;

import dev.langchain4j.store.embedding.weaviate.WeaviateEmbeddingStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.langchain4j.model.chat.ChatLanguageModel;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RagConfig {

    /**
     * Retriever → pulls relevant chunks from Weaviate
     */
    @Bean
    public ContentRetriever pdfRetriever(WeaviateEmbeddingStore store,
                                         EmbeddingModel embeddingModel) {

        return new EmbeddingStoreContentRetriever(store, embeddingModel);
    }

    /**
     * RetrievalAugmentor → transforms user query into RAG-augmented prompt
     */
    @Bean
    public RetrievalAugmentor pdfAugmentor(ContentRetriever pdfRetriever) {

        return DefaultRetrievalAugmentor.builder()
                .contentRetriever(pdfRetriever)
                .build();
    }

    /**
     * AiService → clean interface for answering queries
     * Automatically:
     *  - runs retrieval
     *  - augments prompt
     *  - invokes LLM
     */
    @Bean
    public PdfQaService pdfQaService(ChatLanguageModel chatModel,
                                     RetrievalAugmentor pdfAugmentor) {

        return AiServices.builder(PdfQaService.class)
                .chatLanguageModel(chatModel)
                .retrievalAugmentor(pdfAugmentor)
                .build();
    }
}