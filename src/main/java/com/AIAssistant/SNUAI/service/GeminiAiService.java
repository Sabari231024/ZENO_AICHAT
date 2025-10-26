package com.AIAssistant.SNUAI.service;

import com.AIAssistant.SNUAI.prompts.Prompts;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.stereotype.Service;

import java.util.LinkedList;


@Service
public class GeminiAiService {

    private final Client client;
    private final LinkedList<String> conversationHistory; // store recent chats
    private static final int MAX_HISTORY = 10; // 5 user + 5 AI = 10 entries

    public GeminiAiService() {
        this.client = new Client();
        this.conversationHistory = new LinkedList<>();
    }

    /**
     * Sends a message to ZENO and preserves only the last 5 exchanges.
     * Supports code execution prompts as well.
     */
    public String generateText(String userMessage) {
        try {
            // Add user message
            conversationHistory.add("User: " + userMessage);

            // Keep only the last MAX_HISTORY entries
            while (conversationHistory.size() > MAX_HISTORY) {
                conversationHistory.removeFirst();
            }

            // Build prompt with ZENO system instruction + recent history
            StringBuilder fullPrompt = new StringBuilder(Prompts.ZENO_PROMPT + "\n\n");
            for (String msg : conversationHistory) {
                fullPrompt.append(msg).append("\n");
            }
            fullPrompt.append("AI:"); // signal model to respond

            // Generate response
            GenerateContentResponse response = client.models.generateContent(
                    "gemini-2.0-flash",
                    fullPrompt.toString(),
                    null
            );

            String aiText = response.text();

            // Add AI response to conversation history
            conversationHistory.add("AI: " + aiText);

            // Maintain MAX_HISTORY
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
