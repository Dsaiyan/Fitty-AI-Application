package org.fitness.activityservice.dto;

import lombok.Data;
import org.fitness.activityservice.models.ActivityType;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ActivityResponseDto {
    private String id;
    private String userId ;
    private ActivityType type;
    private Integer duration; // in minutes
    private Integer caloriesBurned;
    private LocalDateTime startTime;
    private Map<String, Object> additionalData; // For any extra data like distance, heart rate, etc.
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
