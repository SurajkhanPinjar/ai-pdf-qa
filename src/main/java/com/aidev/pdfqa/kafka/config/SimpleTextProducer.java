package com.aidev.pdfqa.kafka.config;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SimpleTextProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "demo-text-topic";

    public void sendText(String message) {
        kafkaTemplate.send(TOPIC, message);
        System.out.println("📤 Sent to Kafka: " + message);
    }
}