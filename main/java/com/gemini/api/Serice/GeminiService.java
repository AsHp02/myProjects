package com.gemini.api.Serice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.gemini.api.entity.PormptResponse;
import com.gemini.api.repo.Repositoryp;

@Service
public class GeminiService {

    // Inject the Gemini API key from application.properties or environment variable
    @Value("${gemini.api.key}")
    private String geminiApiKey;

    // Base URL for the Gemini API (using gemini-2.0-flash model)
    private static final String GEMINI_API_BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";


    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final Repositoryp repositoryp;
    // Constructor for dependency injection
    public GeminiService(RestTemplate restTemplate, ObjectMapper objectMapper, Repositoryp repositoryp) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.repositoryp =repositoryp;
    }

    /**
     * Generates text using the Gemini API based on the provided prompt.
     *
     * @param prompt The text prompt to send to the LLM.
     * @return The generated text from the LLM.
     */
    public String generateText(String prompt) {
        try {
            // Build the API URL with the API key
            String apiUrl = GEMINI_API_BASE_URL + "?key=" + geminiApiKey;

            // Create the request payload using Jackson's ObjectMapper
            ObjectNode payload = objectMapper.createObjectNode();

            // Create the contents array with proper structure
            ArrayNode contentsArray = objectMapper.createArrayNode();
            ObjectNode content = objectMapper.createObjectNode();

            // Create parts array
            ArrayNode partsArray = objectMapper.createArrayNode();
            ObjectNode textPart = objectMapper.createObjectNode();
            textPart.put("text", prompt);
            partsArray.add(textPart);

            content.set("parts", partsArray);
            contentsArray.add(content);
            payload.set("contents", contentsArray);

            // Add safety settings and other required fields
            ObjectNode safetySettings = objectMapper.createObjectNode();
            payload.set("safetySettings", objectMapper.createArrayNode());

            // Make the POST request to the Gemini API
            JsonNode response = restTemplate.postForObject(apiUrl, payload, JsonNode.class);

            // Parse the response to extract the generated text
            String generatedText = "No text generated or unexpected response format.";

            if (response != null && response.has("candidates") && response.get("candidates").isArray()) {
                JsonNode firstCandidate = response.get("candidates").get(0);
                if (firstCandidate != null && firstCandidate.has("content") &&
                        firstCandidate.get("content").has("parts") &&
                        firstCandidate.get("content").get("parts").isArray()) {
                    JsonNode firstPart = firstCandidate.get("content").get("parts").get(0);
                    if (firstPart != null && firstPart.has("text")) {
                     generatedText= firstPart.get("text").asText();
                    }
                }
            }
            PormptResponse pormptResponse= new PormptResponse(prompt,generatedText );
            repositoryp.save(pormptResponse);
            return generatedText;

        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace(); // Added for better debugging
            String errorMessage = "Error: Could not generate text. " + e.getMessage();


            PormptResponse pormptResponse = new PormptResponse(prompt, errorMessage);
            repositoryp.save(pormptResponse);
           // PromptResponse promptResponse = new PromptResponse(prompt, errorMessage);
            //promptResponseRepository.save(promptResponse);
            return errorMessage;

        }
    }
}