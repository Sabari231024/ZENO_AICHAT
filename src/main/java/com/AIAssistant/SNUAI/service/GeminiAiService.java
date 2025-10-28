package com.AIAssistant.SNUAI.service;

import com.AIAssistant.SNUAI.prompts.Prompts;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class GeminiAiService {

    private final Client client;
    private final LinkedList<String> conversationHistory;
    private static final int MAX_HISTORY = 10;

    public GeminiAiService() {
        this.client = new Client();
        this.conversationHistory = new LinkedList<>();
    }

    public String generateText(String userMessage, String modelName) {
        try {
            conversationHistory.add("User: " + userMessage);
            while (conversationHistory.size() > MAX_HISTORY) {
                conversationHistory.removeFirst();
            }

            StringBuilder fullPrompt = new StringBuilder(Prompts.ZENO_PROMPT + "\n\n");
            for (String msg : conversationHistory) {
                fullPrompt.append(msg).append("\n");
            }
            fullPrompt.append("AI:");

            GenerateContentResponse response = client.models.generateContent(
                    modelName,
                    fullPrompt.toString(),
                    null
            );

            String aiText = response.text();
            conversationHistory.add("AI: " + aiText);

            while (conversationHistory.size() > MAX_HISTORY) {
                conversationHistory.removeFirst();
            }

            return aiText;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating text: " + e.getMessage();
        }
    }
}