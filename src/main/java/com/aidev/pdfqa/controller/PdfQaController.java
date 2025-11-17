package com.aidev.pdfqa.controller;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.rag.AugmentationRequest;
import dev.langchain4j.rag.AugmentationResult;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.query.Metadata;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//@RestController
//public class PdfQAController {
//
//    private final RetrievalAugmentor augmentor;
//
//    public PdfQAController(RetrievalAugmentor augmentor) {
//        this.augmentor = augmentor;
//    }
//
//    @GetMapping("/answer")
//    public String answer(@RequestParam String q) {
//
//        UserMessage userMessage = UserMessage.userMessage(q);
//
//        // Metadata required (cannot be null in LC4J 0.33.0)
//        Metadata metadata = Metadata.from(
//                userMessage,
//                null,      // no memory id
//                List.of()  // no chat history
//        );
//
//        AugmentationRequest request = new AugmentationRequest(
//                userMessage,
//                metadata
//        );
//
//        AugmentationResult result = augmentor.augment(request);
//
//        ChatMessage answer = result.chatMessage();
//
//        return answer.text();
//    }
//}


import com.aidev.pdfqa.service.PdfQaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pdf")
@RequiredArgsConstructor
public class PdfQaController {

    private final PdfQaService pdfQaService;

    /**
     * Ask a question to the RAG-powered PDF QA system.
     * ChatGPT-style clean answers only.
     */
    @GetMapping("/answer")
    public String answer(@RequestParam String q) {

        // Directly call your AI Service (fully RAG enabled)
        String answer = pdfQaService.answer(q);

        return answer;
    }
}