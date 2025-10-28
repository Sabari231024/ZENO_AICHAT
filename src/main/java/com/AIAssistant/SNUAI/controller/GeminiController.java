package com.AIAssistant.SNUAI.controller;

import com.AIAssistant.SNUAI.service.GeminiAiService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class GeminiController {

    private final GeminiAiService geminiAiService;

    public GeminiController(GeminiAiService geminiAiService) {
        this.geminiAiService = geminiAiService;
    }

    @PostMapping
    public String chat(@RequestBody Map<String, String> payload) {
        String prompt = payload.get("prompt");
        String model = payload.getOrDefault("model", "gemini-2.0-flash");

        if (prompt == null || prompt.trim().isEmpty()) {
            return "⚠️ No message received. Please enter a prompt.";
        }

        return geminiAiService.generateText(prompt, model);
    }
}
