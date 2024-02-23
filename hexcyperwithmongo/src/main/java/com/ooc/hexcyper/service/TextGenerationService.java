package com.ooc.hexcyper.service;

import org.springframework.stereotype.Service;

@Service
public class TextGenerationService {

    public String getChatbotResponse(String prompt) {
        // Implement your logic to generate a chatbot response based on the prompt
        // For example, you can use OpenAI's GPT-3 API or any other method you prefer
        return "Chatbot response for: " + prompt;
    }
}
