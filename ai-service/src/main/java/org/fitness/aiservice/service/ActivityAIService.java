package org.fitness.aiservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fitness.aiservice.model.Activity;
import org.fitness.aiservice.model.Recommendation;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAIService {
    private final GeminiService geminiService;
    private final AiRecommendationService aiRecommendationService;

    // Main method to generate activity analysis and save recommendation
    public void generateActivityAnalysis(Activity activity) {
        String prompt = createPrompt(activity);
        String aiResponse = geminiService.getGeminiResponse(prompt);
        //log.info("Generated Analysis: {}", aiResponse);
        Recommendation recommendation = createRecommendation(activity,aiResponse);
        aiRecommendationService.saveRecommendation(recommendation);
    }

    // Method to create Recommendation from AI response
    public Recommendation createRecommendation(Activity activity, String aiResponse) {
        // Implement the logic to create the recommendation based on the AI response
        // here Parse the aiResponse from the json format to recommendation object
        try {
            // Use a JSON library like Jackson or Gson to parse the response
            // For example, using Jackson:
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(aiResponse);

            JsonNode textNode = rootNode.path("candidates").get(0)
                    .path("content")
                    .path("parts").get(0)
                    .path("text");
            //format to string for better readability and remove unnecessary escape characters
            String recommendationText = textNode.asText()
                    .replaceAll("```json\\n", "")
                    .replaceAll("\\n```", "")
                    .trim();
            //log.info("Parsed from ai-response : {}", recommendationText);

            //After Parsed the cleaned JSON string get Analysis, Improvements, Suggestions and SafetyTips
            JsonNode analysisJson = objectMapper.readTree(recommendationText);

            //1. Parse analysis into recommendation String
            JsonNode analysisNode = analysisJson.path("analysis");
            StringBuilder fullAnalysis = new StringBuilder();
            addAnalysisSection(fullAnalysis, analysisNode, "overall", "Overall: ");
            addAnalysisSection(fullAnalysis, analysisNode, "pace", "Pace: ");
            addAnalysisSection(fullAnalysis, analysisNode, "heartRate", "Heart Rate: ");
            addAnalysisSection(fullAnalysis, analysisNode, "caloriesBurned", "Calories Burned: ");

            //2. Parse improvements into List<String> as improvements in Recommendation model
            List<String> improvements = extractImprovements(analysisJson.path("improvements"));

            //3. Parse suggestions into List<String> as suggestions in Recommendation model
            List<String> suggestions = extractSuggestions(analysisJson.path("suggestions"));

            //4. Parse safetyTips into List<String> as safetyTips in Recommendation model
            List<String> safetyTips = extractSafetyTips(analysisJson.path("safetyTips"));

            //Log the final recommendation object
            //log.info("Final Recommendation Object: {}", recommendation);
            // Here you can return and save the recommendation to the database
            return Recommendation.builder()
                    .activityId(activity.getId())
                    .userId(activity.getUserId())
                    .activityType(activity.getType())
                    .recommendationText(fullAnalysis.toString())
                    .improvements(improvements)
                    .suggestions(suggestions)
                    .safetyTips(safetyTips)
                    .createdAt(LocalDateTime.now())
                    .build();

        } catch (Exception e) {
            log.error("Error parsing AI response: {}", e.getMessage());
            return createDefaultRecommendation(activity);
        }
    }
    // Create a default recommendation in case of parsing errors
    private Recommendation createDefaultRecommendation(Activity activity) {
        return Recommendation.builder()
                .activityId(activity.getId())
                .userId(activity.getUserId())
                .activityType(activity.getType())
                .recommendationText("No detailed analysis available.")
                .improvements(Collections.singletonList("Continue current routine, no specific improvements needed!"))
                .suggestions(Collections.singletonList("Consider consulting a fitness professional for personalized advice."))
                .safetyTips(Arrays.asList(
                        "Always warm up before exercising to prevent injuries.",
                        "Stay hydrated by drinking water before, during, and after workouts.",
                        "Use proper form and technique to avoid strain and injury.",
                        "Listen to your body and rest if you feel pain or discomfort.",
                        "Cool down after workouts to help your body recover."))
                .createdAt(LocalDateTime.now())
                .build();
    }

    // Helper method to append analysis sections if they exist
    private void addAnalysisSection(StringBuilder fullAnalysis,
                                    JsonNode analysisNode, String key, String prefix) {
        if (!analysisNode.path(key).isMissingNode()) {
            fullAnalysis.append(prefix)
                    .append(analysisNode.path(key).asText())
                    .append(System.lineSeparator());
        }
    }
    // Method to extract improvements from JSON node
    private List<String> extractImprovements(JsonNode improvements){
        List<String> improvementList = new ArrayList<>();
        if(improvements.isArray()){
            for(JsonNode improvement : improvements){
                String area = improvement.path("area").asText();
                String recommendation = improvement.path("recommendation").asText();
                improvementList.add(area + " : " + recommendation);
            }
        }
        return improvementList.isEmpty() ?
                Collections.singletonList("No Specific improvement needed for now!") : improvementList;
    }

    // Stub methods for extracting suggestions and safety tips
    private List<String> extractSuggestions(JsonNode suggestions) {
        List<String> suggestionList = new ArrayList<>();
        if(suggestions.isArray()){
            for(JsonNode suggestion : suggestions){
                String workout = suggestion.path("workout").asText();
                String description = suggestion.path("description").asText();
                suggestionList.add(workout + " : " + description);
            }
        }
        return suggestionList.isEmpty() ?
                Collections.singletonList("No Specific suggestions for now!") : suggestionList;
    }

    // Stub methods for extracting suggestions and safety tips
    private List<String> extractSafetyTips(JsonNode safetyTips) {
        List<String> safetyTipList = new ArrayList<>();
        if(safetyTips.isArray()){
            for(JsonNode tip : safetyTips){
                String safetyTip = tip.path("tip").asText();
                String explanation = tip.path("explanation").asText();
                safetyTipList.add(safetyTip + " : " + explanation);
            }
        }
        return safetyTipList.isEmpty() ?
                Collections.singletonList("No Specific safety tips for now!") : safetyTipList;
    }

    public String createPrompt(Activity activity) {
        return String.format("""
                Analyze this fitness activity data and provide detailed recommendations in the following EXACT JSON format:
                {
                  "analysis":{
                    "overall":"detailed overall analysis of the activity",
                    "pace":"pace analysis here",
                    "heartRate":"heart rate analysis here",
                    "caloriesBurned":"calories burned analysis here",
                  },
                  "improvements":[
                    {
                        "area":"specific area to improve",
                        "recommendation":"detailed recommendation"
                    }
                  ],
                  "suggestions":[
                    {
                        "workout":"workout name",
                        "description":"detailed workout description",
                    }
                  ],
                  "safetyTips":[
                    {
                        "tip":"specific safety tip",
                        "explanation":"brief explanation"
                    }
                  ]
                }
                Analyze the following activity data and provide the recommendations in the above specified JSON format.
                Activity Data:
                Activity Type : %s
                Duration : %d minutes
                Calories Burned : %d
                Additional Data : %s
               \s
                Provide details analysis focusing on performance, improvements, next workouts suggestions and safety guidelines.
                Ensure the response is strictly in the specified JSON format without any additional text.
               \s""",
                activity.getType(),
                activity.getDuration(),
                activity.getCaloriesBurned(),
                activity.getAdditionalData().toString()
        );
    }
}
