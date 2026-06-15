package com.ragDemo.ragApp.rag.ingestion;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ragDemo.ragApp.rag.model.DocumentChunk;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;

@Service
public class DocumentIngestionService {

    private final EmbeddingModel embeddingModel;
    private final EmbeddingStore<TextSegment> embeddingStore;

    public DocumentIngestionService(
            EmbeddingModel embeddingModel,
            EmbeddingStore<TextSegment> embeddingStore) {

        this.embeddingModel = embeddingModel;
        this.embeddingStore = embeddingStore;
    }

    public void ingest(
            List<DocumentChunk> chunks) {

        for (DocumentChunk chunk : chunks) {

            if (chunk.getContent() == null
                    || chunk.getContent().isBlank()) {
                continue;
            }

            TextSegment segment = TextSegment.from(
                    "Section: "
                            + chunk.getTitle()
                            + "\n\n"
                            + chunk.getContent());

            Embedding embedding = embeddingModel
                    .embed(segment.text())
                    .content();

            embeddingStore.add(
                    embedding,
                    segment);

            System.out.println(
                    "Stored chunk : "
                            + chunk.getTitle());
        }
    }
}