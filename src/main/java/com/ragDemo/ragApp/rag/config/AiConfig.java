package com.ragDemo.ragApp.rag.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

@Configuration
public class AiConfig {

    @Bean
    public EmbeddingModel embeddingModel() {

        return OllamaEmbeddingModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("nomic-embed-text")
                .timeout(Duration.ofMinutes(2))
                .build();
    }

    @Bean
    public ChatModel chatModel() {

        return OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("llama3.2")
                .temperature(0.0)
                .timeout(Duration.ofMinutes(2))
                .build();
    }

    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {

        return new InMemoryEmbeddingStore<>();
    }
}