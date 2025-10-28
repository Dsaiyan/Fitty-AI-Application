package org.fitness.aiservice.model;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class Activity {
    private String id;
    private String userId ;
    private String type;
    private Integer duration; // in minutes
    private Integer caloriesBurned;
    private LocalDateTime startTime;
    private Map<String, Object> additionalData; // For any extra data like distance, heart rate, etc.
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
