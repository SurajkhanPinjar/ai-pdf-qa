package com.aidev.pdfqa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AiPdfController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello macha! Swagger is working!";
    }

    @GetMapping("/status")
    public String status() {
        return "App is running fine 🚀";
    }
}