package com.ooc.hexcyper.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

@Service
public class VisionService {

    public String analyzeImage(String imagePath) {
        try {
            // Read image file and encode to base64
            String base64Image = encodeImageToBase64(imagePath);

            // Construct the JSON payload
            String jsonPayload = String.format("{\"contents\":[{\"parts\":[{\"text\":\"What is this picture?\"},{\"inline_data\":{\"mime_type\":\"image/jpeg\",\"data\":\"%s\"}}]}]}", base64Image);

            // Create HttpClient
            HttpClient httpClient = HttpClientBuilder.create().build();

            // Create HTTP POST request
            HttpPost request = new HttpPost("https://generativelanguage.googleapis.com/v1beta/models/gemini-pro-vision:generateContent?key=AIzaSyB919rVpqzi2BmD72RF7mQH7Xmd-72qdE0");
            request.setHeader("Content-Type", "application/json");

            // Set JSON payload
            StringEntity entity = new StringEntity(jsonPayload);
            request.setEntity(entity);

            // Execute request and get response
            HttpResponse response = httpClient.execute(request);

            // Read response content
            String responseBody = EntityUtils.toString(response.getEntity());

            // Filter and extract content text from JSON response
            return extractContentText(responseBody);
        } catch (IOException e) {
            e.printStackTrace();
            return "Error analyzing image.";
        }
    }

    private String encodeImageToBase64(String imagePath) throws IOException {
        byte[] imageBytes = Files.readAllBytes(Path.of(imagePath));
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    private String extractContentText(String responseBody) {
        JsonObject responseJson = JsonParser.parseString(responseBody).getAsJsonObject();
        JsonArray candidates = responseJson.getAsJsonArray("candidates");
        if (candidates != null && candidates.size() > 0) {
            JsonObject firstCandidate = candidates.get(0).getAsJsonObject();
            JsonObject content = firstCandidate.getAsJsonObject("content");
            JsonArray parts = content.getAsJsonArray("parts");
            StringBuilder contentTextBuilder = new StringBuilder();
            for (JsonElement part : parts) {
                JsonObject partObj = part.getAsJsonObject();
                String text = partObj.get("text").getAsString();
                contentTextBuilder.append(text).append("\n");
            }
            return contentTextBuilder.toString().trim();
        } else {
            return "No content found in the response.";
        }
    }
}
