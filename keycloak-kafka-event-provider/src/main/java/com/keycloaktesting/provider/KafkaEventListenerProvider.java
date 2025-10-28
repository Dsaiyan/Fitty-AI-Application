package com.keycloaktesting.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.logging.Logger;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;

public class KafkaEventListenerProvider implements EventListenerProvider {
    private static final Logger log = Logger.getLogger(KafkaEventListenerProvider.class);
    private final ObjectMapper mapper = new ObjectMapper();

    public KafkaEventListenerProvider(KeycloakSession session) {
        // Nothing needed — uses static KafkaProducerManager
    }

    @Override
    public void onEvent(Event event) {
        try {
            String json = mapper.writeValueAsString(event);
            KafkaProducerManager.send(json);
            log.debugf("User event published to Kafka: %s", event.getType());
        } catch (Exception e) {
            log.error("Error serializing user event", e);
        }
    }

    @Override
    public void onEvent(AdminEvent event, boolean includeRepresentation) {
        try {
            String json = mapper.writeValueAsString(event);
            KafkaProducerManager.send(json);
            log.debugf("Admin event published to Kafka: %s", event.getOperationType());
        } catch (Exception e) {
            log.error("Error serializing admin event", e);
        }
    }

    @Override
    public void close() {
        //KafkaProducerManager.close();
        //Do not close producer here — Keycloak calls this per-request
    }
}
