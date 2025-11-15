package com.aidev.pdfqa.kafka.producer;

import com.aidev.pdfqa.kafka.dto.PdfUploadedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PdfUploadProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    public void sendPdfUploadedEvent(PdfUploadedEvent event) {
        try {
            String payload = mapper.writeValueAsString(event);
            kafkaTemplate.send("pdf.uploaded", payload);
            System.out.println("📤 Sent Kafka event → " + payload);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}