package com.aidev.pdfqa.controller;

import com.aidev.pdfqa.kafka.config.SimpleTextProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class KafkaTestController {

    private final SimpleTextProducer producer;

    @PostMapping("/send")
    public String send(@RequestParam String message) {
        producer.sendText(message);
        return "Message sent: " + message;
    }
}