package com.aidev.pdfqa.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PdfUploadedEvent {
    private String fileName;
    private String path;
}