package com.aidev.pdfqa.controller;

import com.aidev.pdfqa.kafka.dto.PdfUploadedEvent;
import com.aidev.pdfqa.kafka.producer.PdfUploadProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pdf")
public class PdfUploadController {

    private final PdfUploadProducer producer;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadPdf(@RequestParam("file") MultipartFile file) throws Exception {

        String fileName = System.currentTimeMillis() + "-" + StringUtils.cleanPath(file.getOriginalFilename());
        String savePath = "/tmp/pdfs/" + fileName;

        File folder = new File("/tmp/pdfs/");
        if (!folder.exists()) folder.mkdirs();

        file.transferTo(new File(savePath));

        // Create DTO event
        PdfUploadedEvent event = new PdfUploadedEvent(fileName, savePath);

        // Send to Kafka
        producer.sendPdfUploadedEvent(event);

        return ResponseEntity.accepted().body("📤 PDF uploaded. Processing will start in background.");
    }
}