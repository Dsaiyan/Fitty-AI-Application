package com.keycloaktesting.provider;

import org.jboss.logging.Logger;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class KafkaEventListenerProviderFactory implements EventListenerProviderFactory {
    private static final Logger log = Logger.getLogger(KafkaEventListenerProviderFactory.class);
    private static final String PROVIDER_ID = "kafka-event-listener";

    @Override
    public EventListenerProvider create(KeycloakSession session) {
        return new KafkaEventListenerProvider(session);
    }

    @Override
    public void init(org.keycloak.Config.Scope config) {
        log.info("Initializing KafkaEventListenerProviderFactory");
        KafkaProducerManager.initialize() ;
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {}

    @Override
    public void close() {
        log.info("Closing KafkaEventListenerProviderFactory");
        KafkaProducerManager.close();
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }
}
