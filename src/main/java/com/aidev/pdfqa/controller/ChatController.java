package com.aidev.pdfqa.controller;

import dev.langchain4j.model.ollama.OllamaChatModel;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final OllamaChatModel ollamaChatModel;

    public ChatController(OllamaChatModel ollamaChatModel) {
        this.ollamaChatModel = ollamaChatModel;
    }

    @Operation(summary = "Ask Ollama a question")
    @GetMapping("/api/chat")
    public String chat(@RequestParam("q") String question) {
        // synchronous, simple call
        return ollamaChatModel.generate(question);
    }
}