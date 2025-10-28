package org.fitness.aiservice.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RecommendationDTO {
    private String activityId;
    private String userId ;
    private String activityType;
    private String recommendationText;
    private List<String> improvements;
    private List<String> suggestions;
    private List<String> safetyTips;
    private LocalDateTime createdAt;
}
