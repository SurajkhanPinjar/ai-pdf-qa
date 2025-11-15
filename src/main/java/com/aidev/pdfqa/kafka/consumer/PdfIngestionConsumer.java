package com.aidev.pdfqa.kafka.consumer;

import com.aidev.pdfqa.kafka.dto.PdfUploadedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.weaviate.WeaviateEmbeddingStore;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PdfIngestionConsumer {

    private final WeaviateEmbeddingStore weaviateStore;
    private final EmbeddingModel embeddingModel; // <-- ADD THIS
    private final ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(topics = "pdf.uploaded", groupId = "pdf-ingestion-group")
    public void processPdf(String message) throws Exception {

        PdfUploadedEvent event = mapper.readValue(message, PdfUploadedEvent.class);
        System.out.println("📥 Kafka: Processing " + event.getFileName());

        // Load PDF
        PDDocument doc = PDDocument.load(new File(event.getPath()));
        String text = new PDFTextStripper().getText(doc);
        doc.close();

        // Chunk
        String[] chunks = text.split("\\n\\n");

        // Embed + store
        for (String chunk : chunks) {
            if (chunk.trim().isEmpty()) continue;

            TextSegment segment = TextSegment.from(chunk);

            // 1) Embed
            Embedding embedding = embeddingModel.embed(segment).content();

            // 2) Store in Weaviate with metadata
            weaviateStore.add(
                    UUID.randomUUID().toString(),
                    embedding
            );
        }

        System.out.println("✅ Completed ingestion for: " + event.getFileName());
    }
}