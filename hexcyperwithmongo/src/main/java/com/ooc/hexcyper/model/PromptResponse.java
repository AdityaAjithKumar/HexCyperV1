package com.ooc.hexcyper.model;

public class PromptResponse {
    private String userId;
    private String prompt;
    private String response;
    private String image; // Add image field

    public PromptResponse() {
        // Default constructor
    }

    public PromptResponse(String userId, String prompt, String response, String image) { // Modify constructor
        this.userId = userId;
        this.prompt = prompt;
        this.response = response;
        this.image = image; // Set image field
    }

    // Getters and setters for image field
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    // Getters and setters for existing fields (userId, prompt, response)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
