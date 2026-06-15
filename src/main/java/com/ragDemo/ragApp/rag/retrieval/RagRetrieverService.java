package com.ragDemo.ragApp.rag.retrieval;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;

@Service
public class RagRetrieverService {

    private static final int TOP_K = 3;

    private final EmbeddingModel embeddingModel;
    private final EmbeddingStore<TextSegment> embeddingStore;

    public RagRetrieverService(
            EmbeddingModel embeddingModel,
            EmbeddingStore<TextSegment> embeddingStore) {

        this.embeddingModel = embeddingModel;
        this.embeddingStore = embeddingStore;
    }

    public List<EmbeddingMatch<TextSegment>> retrieve(
            String question) {

        Embedding queryEmbedding = embeddingModel
                .embed(question)
                .content();

        EmbeddingSearchResult<TextSegment> result = embeddingStore.search(
                EmbeddingSearchRequest
                        .builder()
                        .queryEmbedding(
                                queryEmbedding)
                        .maxResults(TOP_K)
                        .build());

        result.matches().forEach(match -> {

            System.out.println(
                    "\n==============================");

            System.out.println(
                    "Score : "
                            + match.score());

            System.out.println(
                    match.embedded().text());
        });

        return result.matches();
    }
}