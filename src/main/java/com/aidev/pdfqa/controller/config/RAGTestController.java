package com.aidev.pdfqa.controller.config;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.rag.AugmentationRequest;
import dev.langchain4j.rag.AugmentationResult;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.query.Metadata;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RAGTestController {

    private final RetrievalAugmentor augmentor;

    public RAGTestController(RetrievalAugmentor augmentor) {
        this.augmentor = augmentor;
    }

    @GetMapping("/api/debug/rag")
    public String testRag(@RequestParam(defaultValue = "What does the PDF say?") String q) {

        // Build the UserMessage
        UserMessage userMessage = UserMessage.userMessage(q);

        // Build Metadata (cannot be null for your version)
        Metadata metadata = Metadata.from(
                userMessage,  // original user message
                null,         // no chat memory id
                null          // no past history
        );

        // Build AugmentationRequest
        AugmentationRequest request = new AugmentationRequest(
                userMessage,
                metadata
        );

        // Perform RAG
        AugmentationResult result = augmentor.augment(request);

        ChatMessage augmentedMessage = result.chatMessage();

        return """
                🔍 RAG TEST RESULT (Your LC4J 0.33.0 API)
                -----------------------------------------

                Original Query:
                %s

                Augmented Prompt:
                %s

                Retrieved Contents:
                %s

                """
                .formatted(
                        q,
                        augmentedMessage.text(),   // augmented RAG prompt
                        result.contents()           // may be null unless retriever supplies contents
                );
    }
}