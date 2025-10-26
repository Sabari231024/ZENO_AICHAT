package com.AIAssistant.SNUAI.controller;

import com.AIAssistant.SNUAI.service.GeminiAiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeminiController {

    private final GeminiAiService geminiService;

    public GeminiController(GeminiAiService geminiService) {
        this.geminiService = geminiService;
    }

    @GetMapping("/generate")
    public String generate(@RequestParam(defaultValue = "Explain AI in simple terms") String prompt) {
        return geminiService.generateText(prompt);
    }
}
