package com.ragDemo.ragApp.rag.controller;

import org.springframework.web.bind.annotation.*;

import com.ragDemo.ragApp.rag.dto.AskRequest;
import com.ragDemo.ragApp.rag.dto.AskResponse;
import com.ragDemo.ragApp.rag.service.RagService;

@RestController
@RequestMapping("/api/rag")
public class RagController {

    private final RagService ragService;

    public RagController(
            RagService ragService) {

        this.ragService = ragService;
    }

    @PostMapping("/ask")
    public AskResponse ask(
            @RequestBody AskRequest request) {

        String answer = ragService.ask(
                request.getQuestion());

        return new AskResponse(answer);
    }
}