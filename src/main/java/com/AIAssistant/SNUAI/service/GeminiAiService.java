package com.AIAssistant.SNUAI.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.stereotype.Service;

@Service
public class GeminiAiService {

    private final Client client;

    public GeminiAiService() {
        // Automatically uses GEMINI_API_KEY from environment variable
        this.client = new Client();
    }

    public String generateText(String prompt) {
        try {
            GenerateContentResponse response = client.models.generateContent(
                    "gemini-2.0-flash",
                    prompt,
                    null
            );
            return response.text();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating text: " + e.getMessage();
        }
    }
}


