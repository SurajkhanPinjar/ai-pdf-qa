package com.aidev.pdfqa.controller.config;

import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ModelTestController {

    private final OllamaChatModel chatModel;
    private final EmbeddingModel embeddingModel;

    public ModelTestController(OllamaChatModel chatModel, EmbeddingModel embeddingModel) {
        this.chatModel = chatModel;
        this.embeddingModel = embeddingModel;
    }

    @GetMapping("/api/debug/chat")
    public String testChat(@RequestParam(defaultValue = "hello") String q) {
        return "LLM Response: " + chatModel.generate(q);
    }

    @GetMapping("/api/debug/embed")
    public String testEmbedding(@RequestParam(defaultValue = "test embedding") String text) {
        var embedding = embeddingModel.embed(text).content().vector();
        return "Embedding length: " + embedding.length;
    }
}