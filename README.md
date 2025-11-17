

📘 AI PDF Q&A Assistant (Event-Driven, Kafka + AIServices RAG + Ollama + Weaviate)

A fully event-driven, asynchronous RAG pipeline using Java 17, Spring Boot 3.2, Kafka, Weaviate, and Ollama for LLM-powered PDF Question-Answering.

Built with clean architecture, loose coupling, AIServices, and production-quality patterns.

⸻

🚀 Overview

AI PDF Q&A Assistant allows users to:

📤 Upload any PDF

– Extract text
– Chunk it safely
– Remove emoji/noise
– Generate embeddings via Ollama → nomic-embed-text

📦 Store Vectors

– Weaviate (preferred)
or
– PostgreSQL + pgvector

🤖 Query Using RAG

– Retrieve relevant chunks
– AIServices builds the final prompt
– Mistral (Ollama) answers using ONLY retrieved PDF context
– No hallucinations

⚙️ Entire ingestion is event-driven

PDF processing happens asynchronously through Kafka.

✔ Non-blocking
✔ Fault-tolerant
✔ Horizontally scalable
✔ Decoupled ingestion pipeline
✔ Ready for production

⸻

🧠 Why Kafka?

PDF extraction → chunking → embedding is slow.

Instead of blocking the upload API:

Client Uploads PDF
↓
REST API produces Kafka event → pdf.ingest
↓
Kafka Consumer Worker
↓
Extract → Chunk → Embed → Store vectors

Benefits:
•	✔ Upload API returns instantly
•	✔ No timeout issues
•	✔ Retry on failures
•	✔ Parallel ingestion workers
•	✔ Event-driven microservice style

⸻

🏗️ Tech Stack

Backend
•	Java 17
•	Spring Boot 3.2.x
•	Spring Kafka
•	Spring Web
•	LangChain4j (0.33.0)
•	AIServices API
•	Apache PDFBox

AI & RAG
•	Ollama
•	mistral (chat model)
•	nomic-embed-text (embedding model)
•	RAG using:
•	WeaviateContentRetriever
•	DefaultRetrievalAugmentor
•	AIServices prompt templates

Vector Databases
•	Weaviate (default, best)
•	PostgreSQL + pgvector (optional)

Infra
•	Kafka + Zookeeper
•	Weaviate
•	Docker Compose
•	Ollama (local LLM server)

⸻

📁 Updated Project Structure

ai-pdf-qa/
├── controller/
│   ├── PdfUploadController.java   <-- Upload endpoint
│   ├── PdfQueryController.java    <-- AI Q&A endpoint (AIServices)
│   └── HealthController.java
│
├── kafka/
│   ├── producer/
│   │   └── PdfIngestProducer.java
│   ├── consumer/
│   │   └── PdfIngestConsumer.java
│   ├── model/
│   │   └── PdfUploadedEvent.java
│   └── KafkaConfig.java
│
├── rag/
│   ├── PdfTextExtractor.java
│   ├── ChunkService.java
│   ├── PdfIngestionService.java
│   ├── RagConfig.java             <-- AIServices RAG setup
│   └── PdfQaService.java          <-- AIServices interface
│
├── vector/
│   ├── VectorStoreConfig.java
│   ├── WeaviateVectorStore.java
│   └── PgVectorStore.java
│
├── config/
│   ├── OllamaConfig.java
│   ├── EmbeddingConfig.java
│   ├── SwaggerConfig.java
│   └── AppProperties.java
│
├── model/
│   ├── Chunk.java
│   ├── PdfDocumentMeta.java
│   ├── QueryRequest.java
│   └── QueryResponse.java
│
├── resources/
│   ├── application.yaml
│   ├── application-dev.yaml
│   ├── application-docker.yaml
│   └── banner.txt
│
└── PdfQaApplication.java


⸻

🔄 Event-Driven RAG Pipeline

1️⃣ Upload PDF

POST /api/pdf/upload
•	Saves file temporarily
•	Publishes PdfUploadedEvent to Kafka

2️⃣ Kafka Worker

Consumes event → performs:

✔ PDF text extraction
✔ Cleanup (remove emojis)
✔ Chunking (safe chunk sizes)
✔ Embedding using Ollama (nomic-embed-text)
✔ Insert vectors into Weaviate
✔ Attach metadata:

source: <filename>
type: pdf
path: /tmp/...

3️⃣ Ask Questions

GET /api/pdf/ask?q=

Using AIServices:
•	Embeds question
•	Retrieves top chunks
•	Builds combined prompt
•	Calls Mistral
•	Returns contextual answer

⸻

🤖 AIServices — Our RAG Brain

public interface PdfQaService {

    @SystemMessage("""
        You are an expert PDF assistant.
        Use ONLY the retrieved PDF chunks.
        If answer not found: say "I could not find this in the document."
    """)
    @UserMessage("Question: {{question}}")
    String answer(String question);
}

Wiring in RagConfig:

@Bean
public PdfQaService pdfQaService(OllamaChatModel chatModel,
RetrievalAugmentor augmentor) {
return AiServices.builder(PdfQaService.class)
.chatLanguageModel(chatModel)
.retrievalAugmentor(augmentor)
.build();
}


⸻

🔥 REST Endpoints

📤 Upload PDF

POST /api/pdf/upload
file: <PDF>

Response:

"Upload received! PDF is being processed."

🤖 Ask a question

GET /api/pdf/ask?q=Explain chapter 2

Response:

{
"answer": "Chapter 2 describes...",
"sources": [...]
}


⸻

🐳 Docker Setup

Start everything:

docker-compose up -d

Includes:
•	Kafka
•	Zookeeper
•	Weaviate
•	Ollama (API enabled)

Run backend:

./gradlew bootRun

Swagger:

http://localhost:8081/swagger-ui.html


⸻

🧪 Testing End-to-End

1️⃣ Upload a PDF

curl -F "file=@bank-policy.pdf" http://localhost:8081/api/pdf/upload

2️⃣ Wait for Kafka worker to index it

3️⃣ Ask a question

curl "http://localhost:8081/api/pdf/ask?q=What is the interest rate?"


⸻

🔮 Roadmap
•	PDF page-level source mapping
•	WebSockets streaming answers
•	Multi-PDF collections
•	User accounts + multi-tenancy
•	Automatic PDF summarization
•	Topic extraction
•	Caching layer for faster queries
•	Hybrid RAG (keyword + semantic search)

⸻

🤝 Contributing

PRs welcome!
This repo is built for learning and production-ready experimentation.

⸻

🛡️ License

MIT License

⸻
