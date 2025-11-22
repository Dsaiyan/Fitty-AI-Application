package org.fitness.activityservice.service;

import lombok.RequiredArgsConstructor;
import org.fitness.activityservice.dto.ActivityRequestDto;
import org.fitness.activityservice.dto.ActivityResponseDto;
import org.fitness.activityservice.exceptions.ActivityNotFoundException;
import org.fitness.activityservice.exceptions.UserNotMatchException;
import org.fitness.activityservice.feignClients.AuthServiceFeignClient;
import org.fitness.activityservice.mapper.MapperClass;
import org.fitness.activityservice.models.Activity;
import org.fitness.activityservice.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final MapperClass mapper;
    private final AuthServiceFeignClient authServiceFeignClient;
    private final KafkaTemplate<String,String> kafkaTemplate;
    @Value("${kafka.topic.name}")
    private String topicName;


    public ActivityResponseDto trackActivity(ActivityRequestDto activityRequest) {
        //validate userId by checking via auth service
        if (activityRequest.getUserId() == null || activityRequest.getUserId().isBlank()) {
            throw new UserNotMatchException("Missing user ID in request");
        }
        if(!authServiceFeignClient.validateUser(activityRequest.getUserId())) {
            throw new UserNotMatchException("Invalid user ID: " + activityRequest.getUserId());
        }

        // Log the received additionalData
        System.out.println("Received additionalData: " + activityRequest.getAdditionalData());

        // Convert DTO to entity
        Activity activity = Activity.builder()
                .userId(activityRequest.getUserId())
                .type(activityRequest.getType())
                .duration(activityRequest.getDuration())
                .caloriesBurned(activityRequest.getCaloriesBurned())
                .startTime(activityRequest.getStartTime())
                .additionalData(activityRequest.getAdditionalData())
                // Assuming createdAt and updatedAt are handled by the database
                .build();

        Activity savedActivity = activityRepository.save(activity);
        // sent kafka event to AI service
        sendActivityEvent(savedActivity.getId(), mapper.toJson(savedActivity));
        return mapper.toDto(savedActivity);
    }

    //method for kafka producer to send message to topic
    public void sendActivityEvent(String activityId, String activityJson) {
        kafkaTemplate.send(topicName, activityId, activityJson);
    }

    public List<ActivityResponseDto> getAllActivitiesByUser(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new UserNotMatchException("Missing user ID in request header");
        }
        // Validate userId by checking via auth service
        if(!authServiceFeignClient.validateUser(userId)) {
            throw new UserNotMatchException("Invalid user ID: " + userId);
        }
        // Fetch activities from the repository
        List<Activity> activities = activityRepository.findByUserId(userId);
        if (activities.isEmpty()) {
            throw new ActivityNotFoundException("No activities found for user with ID: " + userId);
        }
        // Convert the list of activities to List of DTOs
        return activities.stream()
                .map(mapper::toDto)
                .toList();
    }

    public ActivityResponseDto getActivityById(String id) {
        Optional<Activity> activityOptional = activityRepository.findById(id);
        if (activityOptional.isEmpty()) {
            throw new ActivityNotFoundException("Activity not found with ID: " + id);
        }
        Activity activity = activityOptional.get();
        // Convert the activity to DTO
        return mapper.toDto(activity);
    }
}
