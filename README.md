-
📘 AI PDF Q&A Assistant (Event-Driven, Kafka + RAG + Ollama)

A scalable, asynchronous, event-driven system to upload PDFs, index them using embeddings, and query them using LLM-powered RAG.

⸻

🚀 Overview

AI PDF Q&A Assistant is a Java 17 + Spring Boot 3.2 application that lets users:
•	Upload any PDF
•	Extract text & chunk it
•	Generate embeddings using LangChain4j + Ollama
•	Store vectors in Weaviate or pgvector
•	Retrieve relevant chunks using RAG
•	Ask natural language questions about the PDF
•	Get AI-generated answers from Mistral (via Ollama)
•	All ingestion happens asynchronously via Kafka

This architecture is highly scalable, resilient, and production-ready.

⸻

🧠 Why Kafka?

PDF ingestion (extract → chunk → embed → vector store) is heavy, so Instead of doing it in the upload API, we push it to Kafka:

User Uploads PDF
↓
Kafka Producer: pdf.ingest event
↓
Kafka Consumer (background worker)
↓
Extract → Chunk → Embed → Vector Store

This gives:

✔ Non-blocking uploads
✔ Retry & fault tolerance
✔ Horizontal scaling of ingestion workers
✔ Separation of concerns
✔ Event-driven pipelines

⸻

🏗️ Tech Stack

Backend
•	Java 17
•	Spring Boot 3.2.x
•	Gradle
•	Spring Kafka
•	LangChain4j
•	Apache PDFBox

AI + RAG
•	Ollama (LLM inference)
•	Mistral (chat model)
•	nomic-embed-text (embedding model)
•	Chunking + Retrieval pipeline

Vector Database
•	Weaviate or
•	Postgres + pgvector

Infrastructure
•	Docker
•	Docker Compose
•	Kafka + Zookeeper

⸻

📁 Project Folder Structure

ai-pdf-qa/
├── src/main/java/com/aidev/pdfqa/
│   ├── controller/
│   │   ├── PdfUploadController.java
│   │   ├── QueryController.java
│   │   └── HealthController.java
│   │
│   ├── kafka/
│   │   ├── producer/
│   │   │   └── PdfIngestProducer.java
│   │   ├── consumer/
│   │   │   └── PdfIngestConsumer.java
│   │   ├── config/
│   │   │   └── KafkaConfig.java
│   │   └── model/
│   │       └── PdfIngestEvent.java
│   │
│   ├── rag/
│   │   ├── PdfTextExtractor.java
│   │   ├── ChunkService.java
│   │   ├── EmbeddingService.java
│   │   ├── PdfIngestionService.java
│   │   └── RAGQueryService.java
│   │
│   ├── vector/
│   │   ├── VectorStoreRepository.java
│   │   ├── PgVectorStore.java
│   │   └── WeaviateVectorStore.java
│   │
│   ├── config/
│   │   ├── OllamaConfig.java
│   │   ├── EmbeddingConfig.java
│   │   ├── VectorStoreConfig.java
│   │   ├── RAGConfig.java
│   │   └── SwaggerConfig.java
│   │
│   ├── model/
│   │   ├── PdfDocumentMeta.java
│   │   ├── Chunk.java
│   │   ├── QueryRequest.java
│   │   └── QueryResponse.java
│   │
│   ├── util/
│   │   └── FileUtils.java
│   │
│   └── PdfQaApplication.java
│
├── src/main/resources/
│   ├── application.yml
│   ├── sample-pdfs/
│   └── banner.txt
│
├── build.gradle.kts
├── docker-compose.yml
├── README.md
└── .gitignore


⸻

🔄 Event-Driven Flow (Kafka)

🔹 1. PDF Upload (REST)

POST /api/pdf/upload

	•	Saves PDF temporarily
	•	Publishes a pdf.ingest event to Kafka

🔹 2. Kafka Consumer runs Ingestion
•	Extracts text
•	Splits into chunks
•	Generates embeddings
•	Stores vectors
•	Deletes temp file

🔹 3. User asks a Question (REST)

GET /api/pdf/ask?q=...

	•	Embeds question
	•	Retrieves top chunks
	•	Builds RAG prompt
	•	Calls Ollama → returns answer

⸻

🔥 Endpoints

Upload PDF

POST /api/pdf/upload
Content-Type: multipart/form-data
file: <your-pdf>

Response:

"Upload received! PDF is being processed."

Ask a Question

GET /api/pdf/ask?q=Summarize chapter 2

Response:

{
"answer": "Chapter 2 mainly discusses...",
"sources": [...]
}


⸻

🐳 Docker Setup

Start the entire stack:

docker-compose up -d

This launches:
•	Kafka + Zookeeper
•	Weaviate (or Postgres pgvector)
•	Ollama (with API)

Then run app:

./gradlew bootRun

Swagger UI:

http://localhost:8080/swagger-ui.html


⸻

🧪 Testing the Flow

1️⃣ Upload a PDF

curl -F "file=@/path/to/file.pdf" http://localhost:8080/api/pdf/upload

2️⃣ Wait for ingestion (~1–3 sec depending on size)

3️⃣ Ask a question

http://localhost:8080/api/pdf/ask?q=What is chapter 1 about?


⸻

🔮 Roadmap
•	Highlight exact PDF page of answer
•	Async response streaming (SSE/WebSockets)
•	Multiple PDFs per user
•	Authorization & user sessions
•	Summaries on upload
•	Topic Extraction
•	RAG with page references

⸻

🤝 Contributing

PRs & suggestions welcome!

⸻

🛡️ License

MIT License

⸻

