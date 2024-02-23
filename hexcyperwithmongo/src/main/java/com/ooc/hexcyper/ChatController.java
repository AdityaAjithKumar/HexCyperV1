package com.ooc.hexcyper;

import com.ooc.hexcyper.model.PromptResponse;
import com.ooc.hexcyper.service.PromptResponseService;
import com.ooc.hexcyper.service.VisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class ChatController {

    private final ImageGeneration imageGeneration;
    private final TextGeneration textGeneration;
    private final PromptResponseService promptResponseService;

    @Autowired
    private VisionService visionService;
    @Autowired
    public ChatController(ImageGeneration imageGeneration, TextGeneration textGeneration, PromptResponseService promptResponseService) {
        this.imageGeneration = imageGeneration;
        this.textGeneration = textGeneration;
        this.promptResponseService = promptResponseService;
    }

    @RequestMapping("/")
    public String homepage(Model model) {
        return "homepage";
    }

    @RequestMapping("/index")
    public String index(Model model) {
        List<PromptResponse> chatHistory = promptResponseService.getChatHistory();
        model.addAttribute("chatHistory", chatHistory);
        return "index";
    }

    @PostMapping("/")
    public String handlePrompt(@RequestParam("prompt") String prompt,
                               @RequestParam(value = "image", required = false) MultipartFile imageFile,
                               Model model) {
        String response;
        if (prompt.startsWith("/text")) {
            response = textGeneration.getChatbotResponse(prompt.substring(5));
        } else if (prompt.startsWith("/image")) {
            response = imageGeneration.getImageGeneration(prompt.substring(6));
        } else if (prompt.startsWith("/cwi")) {
            // Handle chat with image
            if (imageFile != null && !imageFile.isEmpty()) {
                try {
                    // Save the uploaded image to a temporary file
                    File tempFile = File.createTempFile("temp-image", ".jpg");
                    imageFile.transferTo(tempFile);

                    // Analyze the image using the vision service
                    response = visionService.analyzeImage(tempFile.getAbsolutePath());

                    // Delete the temporary file
                    tempFile.delete();
                } catch (IOException e) {
                    e.printStackTrace();
                    response = "Error analyzing image.";
                }
            } else
                response = "Please provide an image.";
            }

         else {
            // Handle case where prompt does not start with /text or /image
            response = "Invalid command. Please start your prompt with /text or /image.";
        }
        promptResponseService.savePromptResponse(prompt, response);
        List<PromptResponse> chatHistory = promptResponseService.getChatHistory();
        model.addAttribute("chatHistory", chatHistory);
        model.addAttribute("response", response);
        return "index";
    }

}
