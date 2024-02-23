package com.ooc.hexcyper.service;

import com.ooc.hexcyper.model.PromptResponse;
import com.ooc.hexcyper.repository.PromptResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PromptResponseService {

    private final PromptResponseRepository promptResponseRepository;
    private final UserService userService;

    @Autowired
    public PromptResponseService(PromptResponseRepository promptResponseRepository, UserService userService) {
        this.promptResponseRepository = promptResponseRepository;
        this.userService = userService;
    }

    public List<PromptResponse> getChatHistory() {
        String userId = userService.getCurrentUserId();
        return promptResponseRepository.findByUserId(userId);
    }

    public PromptResponse savePromptResponse(String prompt, String response) {
        String userId = userService.getCurrentUserId();
        PromptResponse promptResponse = new PromptResponse();
        promptResponse.setUserId(userId);
        promptResponse.setPrompt(prompt);
        promptResponse.setResponse(response);
        return promptResponseRepository.save(promptResponse);
    }
}
