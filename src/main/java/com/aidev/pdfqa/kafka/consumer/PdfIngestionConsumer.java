package com.aidev.pdfqa.kafka.consumer;

import com.aidev.pdfqa.kafka.dto.PdfUploadedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.langchain4j.data.document.Metadata;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PdfIngestionConsumer {

    private final ObjectMapper mapper;
    private final EmbeddingModel embeddingModel;
    private final WeaviateEmbeddingStore weaviateStore;

    @KafkaListener(topics = "pdf.uploaded", groupId = "pdf-ingestion-group")
    public void processPdf(String message) throws Exception {

        PdfUploadedEvent event = mapper.readValue(message, PdfUploadedEvent.class);
        System.out.println("📥 Kafka: Processing " + event.getFileName());

        // 1) Read PDF text using PDFBox
        PDDocument doc = PDDocument.load(new File(event.getPath()));
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(doc);
        doc.close();

        // 2) Split into chunks
        String[] chunks = text.split("\\n\\n");

        // 3) Embed + store each chunk
//        for (String chunk : chunks) {
//
//            if (chunk.trim().isEmpty()) continue;
//
//            TextSegment segment = TextSegment.from(chunk);
//            Embedding embedding = embeddingModel.embed(segment).content();
//            weaviateStore.add(embedding, segment);  // stores with metadata
//        }
        for (String chunk : chunks) {

            if (chunk == null || chunk.isBlank()) continue;

            // 1) Clean bad characters & emojis
            String cleaned = chunk.replaceAll("[^\\p{Print}\\n]", "").trim();
            if (cleaned.isEmpty()) continue;

            // 2) Break into 512-char safe chunks
            List<String> parts = splitIntoChunks(cleaned, 512);

            for (String part : parts) {

                // ===== ADD METADATA HERE =====
                Map<String, Object> md = Map.of(
                        "source", event.getFileName(),
                        "path", event.getPath(),
                        "type", "pdf"
                );
                Metadata metadata = new Metadata(md);

                TextSegment segment = new TextSegment(part, metadata);

                // 3) SAFE embedding
                Embedding embedding = embeddingModel.embed(segment).content();

                // Store embedding + segment + metadata
                weaviateStore.add(embedding, segment);
            }
        }

        System.out.println("✅ Completed ingestion for: " + event.getFileName());
    }

    private List<String> splitIntoChunks(String text, int size) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < text.length(); i += size) {
            list.add(text.substring(i, Math.min(text.length(), i + size)));
        }
        return list;
    }
}