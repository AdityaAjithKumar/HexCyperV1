package com.ooc.hexcyper;

import com.google.gson.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
public class TextGeneration {

    private static final String DEFAULT_MODEL = "gemini-pro";

    @Value("${your.apiKey}")
    private final String apiKey;

    public TextGeneration(@Value("${your.apiKey}") String apiKey) {
        this.apiKey = apiKey;
    }

    public String getChatbotResponse(String prompt) {
        try {
            String endpoint = "https://generativelanguage.googleapis.com/v1beta/models/" + DEFAULT_MODEL + ":generateContent?key=" + apiKey;
            String postData = buildPostData(prompt);

            HttpURLConnection connection = createConnection(endpoint);
            sendPostRequest(connection, postData);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String jsonResponse = readResponse(connection);
                return parseResponse(jsonResponse);
            } else {
                return "Request failed with response code: " + responseCode;
            }

        } catch (IOException e) {
            return "An error occurred: " + e.getMessage();
        }
    }

    private String buildPostData(String prompt) {
        return String.format("{ \"contents\": [{ \"parts\": [{ \"text\": \"%s\" }]}]}", prompt);
    }

    private HttpURLConnection createConnection(String endpoint) throws IOException {
        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        return connection;
    }

    private void sendPostRequest(HttpURLConnection connection, String postData) throws IOException {
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = postData.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
    }

    private String readResponse(HttpURLConnection connection) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder buffer = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        }
    }
    private String parseResponse(String jsonResponse) {
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

        // Check if the response contains "candidates" array
        if (jsonObject.has("candidates")) {
            JsonArray candidatesArray = jsonObject.getAsJsonArray("candidates");
            if (candidatesArray.size() > 0) {
                JsonObject firstCandidate = candidatesArray.get(0).getAsJsonObject();
                if (firstCandidate.has("content")) {
                    JsonObject contentObject = firstCandidate.getAsJsonObject("content");
                    if (contentObject.has("parts")) {
                        JsonArray partsArray = contentObject.getAsJsonArray("parts");
                        if (partsArray.size() > 0) {
                            JsonObject firstPart = partsArray.get(0).getAsJsonObject();
                            if (firstPart.has("text")) {
                                return firstPart.get("text").getAsString();
                            }
                        }
                    }
                }
            }
        }
        return "No response text found";
    }

}
