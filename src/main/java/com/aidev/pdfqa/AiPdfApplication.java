package com.aidev.pdfqa;


import com.aidev.pdfqa.config.WeaviateProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(WeaviateProperties.class)
public class AiPdfApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiPdfApplication.class, args);
	}

}
