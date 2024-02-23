package com.ooc.hexcyper;

import com.google.gson.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
public class ImageGeneration {

    private static final String DEFAULT_MODEL = "sdxl"; // Update with your image generation model


    private final String apiKey;
    private final String apiBase;

    public ImageGeneration(@Value("${your.apiKey}") String apiKey, @Value("${your.apiBase}") String apiBase) {
        this.apiKey = apiKey;
        this.apiBase = apiBase;
    }

    public String getImageGeneration(String prompt) {
        try {
            // Your API endpoint
            String apiUrl = apiBase + "/images/generations";
            // Create JSON payload
            String jsonPayload = String.format("{\"model\":\"%s\", \"prompt\":\"%s\", \"size\":\"1024x1024\", \"n\":1, \"response_format\":\"url\"}",DEFAULT_MODEL, prompt);

            // Build the request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(apiUrl))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            // Send the request
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check the response
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                return "Error: " + response.statusCode() + ", " + response.body();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred: " + e.getMessage();
        }
    }
}