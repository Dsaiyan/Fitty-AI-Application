package org.fitness.activityservice.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.fitness.activityservice.dto.ActivityResponseDto;
import org.fitness.activityservice.models.Activity;
import org.springframework.stereotype.Component;

@Component
public class MapperClass {
    private final ObjectMapper objectMapper;

    public MapperClass() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public ActivityResponseDto toDto(Activity activity) {
        ActivityResponseDto responseDto = new ActivityResponseDto();
        responseDto.setId(activity.getId());
        responseDto.setUserId(activity.getUserId());
        responseDto.setType(activity.getType());
        responseDto.setDuration(activity.getDuration());
        responseDto.setCaloriesBurned(activity.getCaloriesBurned());
        responseDto.setStartTime(activity.getStartTime());
        responseDto.setAdditionalData(activity.getAdditionalData());
        responseDto.setCreatedAt(activity.getCreatedAt());
        responseDto.setUpdatedAt(activity.getUpdatedAt());

        return responseDto;
    }

    public String toJson(Activity savedActivity) {
        try {
            return objectMapper.writeValueAsString(savedActivity);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize Activity to JSON", e);
        }
    }
}
