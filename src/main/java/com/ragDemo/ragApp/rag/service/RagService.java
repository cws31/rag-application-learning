package com.ragDemo.ragApp.rag.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ragDemo.ragApp.rag.retrieval.RagRetrieverService;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;

@Service
public class RagService {

    private final RagRetrieverService retriever;
    private final ChatModel chatModel;

    public RagService(
            RagRetrieverService retriever,
            ChatModel chatModel) {

        this.retriever = retriever;
        this.chatModel = chatModel;
    }

    public String ask(String question) {

        List<EmbeddingMatch<TextSegment>> matches = retriever.retrieve(question);

        if (matches.isEmpty()) {
            return "No relevant information found.";
        }

        StringBuilder context = new StringBuilder();

        for (EmbeddingMatch<TextSegment> match : matches) {

            context.append(
                    match.embedded().text())
                    .append("\n\n");
        }

        String prompt = """
                You are a RAG assistant.

                Answer ONLY from the provided context.

                If the answer is not present in the context,
                reply:

                "Sorry ! the content you are trying to search is not present in the given document."

                Context:
                %s

                Question:
                %s
                """
                .formatted(
                        context,
                        question);

        return chatModel.chat(prompt);
    }
}