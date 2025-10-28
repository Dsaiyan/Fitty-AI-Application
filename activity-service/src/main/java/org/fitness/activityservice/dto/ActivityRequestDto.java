package org.fitness.activityservice.dto;

import lombok.Data;
import org.fitness.activityservice.models.ActivityType;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ActivityRequestDto {
    private String userId;
    private ActivityType type;
    private Integer duration; // in minutes
    private Integer caloriesBurned;
    private LocalDateTime startTime; // Start time of the activity
    public Map<String, Object> additionalData;

    // Additional fields can be added as needed
    // For example, you might want to include a timestamp or location data
}
