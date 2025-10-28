package org.fitness.aiservice.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class GeminiService {
    private final WebClient webClient;
    @Value("${gemini.api.base-url}")
    private String geminiUrl ;
    @Value("${gemini.api.key}")
    private String apiKey ;


    public GeminiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String getGeminiResponse(String prompt) {
        Map<String,Object> requestBody = Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[]{
                                Map.of("text", prompt)
                        } )
                }
        );

        String response = webClient.post()
                .uri(geminiUrl)
                .header("Content-Type", "application/json")
                .header("X-goog-api-key", apiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        //System.out.println(response);
        return response ;
    }
}