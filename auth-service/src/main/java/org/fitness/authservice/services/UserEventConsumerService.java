package org.fitness.authservice.services;


import lombok.RequiredArgsConstructor;
import org.fitness.authservice.exceptions.EmailExistException;
import org.fitness.authservice.models.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserEventConsumerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserEventConsumerService.class) ;
    private final AuthService authService ;


    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeKeycloakEvent(Event event) throws EmailExistException {
        // logging user event id
        LOGGER.info("Consumed Keycloak event -> event_id: {}, userId: {}",
                event.getId(), event.getUserId() );
        authService.consumeEvent(event) ;
    }
}
