package com.aidev.pdfqa.controller;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.rag.AugmentationRequest;
import dev.langchain4j.rag.AugmentationResult;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.query.Metadata;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class RagChatController {

    private final RetrievalAugmentor augmentor;

    @GetMapping("/answer")
    public String answer(@RequestParam String q) {

        UserMessage userMessage = UserMessage.userMessage(q);

        Metadata metadata = Metadata.from(
                userMessage,     // required
                null,            // no memory id
                List.of()        // empty chat memory
        ); // ✅ Required (non-null)

        AugmentationRequest request = new AugmentationRequest(
                userMessage,
                metadata
        );

        AugmentationResult result = augmentor.augment(request);

        return result.chatMessage().text();
    }
}