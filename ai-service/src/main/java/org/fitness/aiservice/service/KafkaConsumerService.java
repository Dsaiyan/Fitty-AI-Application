package org.fitness.aiservice.service;

import lombok.RequiredArgsConstructor;
import org.fitness.aiservice.model.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerService.class);
    private final ActivityAIService activityAIService;

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeActivity(Activity activity) {
        LOGGER.info("#### -> Consumed message -> {}", activity.getId());
        // Process the activity event
        // I want to call this activityAIService.generateActivityAnalysis(activity);
        // For this activity I am getting from kafka topic
        // I want to save the response in recommendation table

        activityAIService.generateActivityAnalysis(activity);
    }
}
