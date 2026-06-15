package com.ragDemo.ragApp.rag.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ragDemo.ragApp.rag.chunking.ResumeChunkingStrategy;
import com.ragDemo.ragApp.rag.ingestion.DocumentIngestionService;
import com.ragDemo.ragApp.rag.model.DocumentChunk;
import com.ragDemo.ragApp.rag.parsing.PdfReaderService;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final PdfReaderService pdfReaderService;
    private final ResumeChunkingStrategy chunker;
    private final DocumentIngestionService ingestionService;

    public DocumentController(
            PdfReaderService pdfReaderService,
            ResumeChunkingStrategy chunker,
            DocumentIngestionService ingestionService) {

        this.pdfReaderService = pdfReaderService;
        this.chunker = chunker;
        this.ingestionService = ingestionService;
    }

    @PostMapping("/upload")
    public String upload(
            @RequestParam("file") MultipartFile file)
            throws Exception {

        String text = pdfReaderService.readPdf(file);

        List<DocumentChunk> chunks = chunker.chunk(text);

        ingestionService.ingest(chunks);

        return "Document uploaded successfully. Total chunks: "
                + chunks.size();
    }

    @GetMapping("/test")
    public String test() {
        return "Working";
    }
}