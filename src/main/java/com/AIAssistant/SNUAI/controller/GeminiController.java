package com.AIAssistant.SNUAI.controller;

import com.AIAssistant.SNUAI.service.GeminiAiService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class GeminiController {

    private final GeminiAiService geminiAiService;

    public GeminiController(GeminiAiService geminiAiService) {
        this.geminiAiService = geminiAiService;
    }

    @PostMapping
    public String chat(@RequestBody String prompt) {
        return geminiAiService.generateText(prompt);
    }
}
