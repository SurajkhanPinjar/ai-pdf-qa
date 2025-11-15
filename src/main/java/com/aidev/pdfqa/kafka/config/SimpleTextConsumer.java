package com.aidev.pdfqa.kafka.config;

import com.aidev.pdfqa.rag.ChunkService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SimpleTextConsumer {

    private final ChunkService chunkService;

    @KafkaListener(topics = "demo-text-topic", groupId = "demo-group")
    public void listen(String message) {
        System.out.println("📥 Received: " + message);
        chunkService.printService(message);
    }
}