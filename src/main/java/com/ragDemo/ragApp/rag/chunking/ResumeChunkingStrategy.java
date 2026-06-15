package com.ragDemo.ragApp.rag.chunking;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ragDemo.ragApp.rag.model.DocumentChunk;

@Service
public class ResumeChunkingStrategy {

    private static final String[] SECTIONS = {
            "Education",
            "Projects",
            "Technical Skills",
            "Soft Skills",
            "Achievements",
            "Social Engagements"
    };

    public List<DocumentChunk> chunk(String text) {

        List<DocumentChunk> chunks = new ArrayList<>();

        int educationIndex = text.indexOf("Education");

        if (educationIndex > 0) {

            chunks.add(
                    DocumentChunk.builder()
                            .title("Profile")
                            .content(
                                    text.substring(
                                            0,
                                            educationIndex)
                                            .trim())
                            .build());
        }

        for (int i = 0; i < SECTIONS.length; i++) {

            String current = SECTIONS[i];

            int start = text.indexOf(current);

            if (start == -1)
                continue;

            int end = text.length();

            if (i < SECTIONS.length - 1) {

                int next = text.indexOf(
                        SECTIONS[i + 1]);

                if (next != -1)
                    end = next;
            }

            chunks.add(
                    DocumentChunk.builder()
                            .title(current)
                            .content(
                                    text.substring(
                                            start,
                                            end)
                                            .trim())
                            .build());
        }

        return chunks;
    }
}