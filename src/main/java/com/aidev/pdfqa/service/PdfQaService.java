package com.aidev.pdfqa.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface PdfQaService {

    @SystemMessage("""
        You are an expert PDF Question-Answering assistant.
        Always answer only using the retrieved PDF context.
        If answer not found, say: 'I could not find this in the document.'
    """)
    @UserMessage("Question: {{question}}")
    String answer(@V("question") String question);
}