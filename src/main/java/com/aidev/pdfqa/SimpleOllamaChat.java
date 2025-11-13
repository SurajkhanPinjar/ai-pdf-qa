package com.aidev.pdfqa;

import dev.langchain4j.model.ollama.OllamaChatModel;

public class SimpleOllamaChat {

    public static void main(String[] args) {

        OllamaChatModel model = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")   // Ollama default
                .modelName("mistral")                // or llama2, codellama, etc.
                .build();

        String response = model.generate("Hello macha, how are you?");
        System.out.println(response);
    }
}
