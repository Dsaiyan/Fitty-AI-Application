package com.keycloaktesting.provider;

import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.jboss.logging.Logger;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;


public class KafkaProducerManager {
    private static final Logger log = Logger.getLogger(KafkaProducerManager.class);
    private static KafkaProducer<String, String> producer;
    private static String topic;
    private static final AtomicBoolean initialized = new AtomicBoolean(false);
    private static final AtomicBoolean closed = new AtomicBoolean(false);

    static {
        initialize();
    }

    public static void initialize() {
        if (initialized.get()) {
            return; // already initialized
        }

        String bootstrapServers = System.getenv().getOrDefault("KAFKA_BOOTSTRAP_SERVERS", "localhost:9092");
        topic = System.getenv().getOrDefault("KAFKA_TOPIC", "keycloak-events");

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 3);

        producer = new KafkaProducer<>(props);
        initialized.set(true);
        closed.set(false);
        log.infof("KafkaProducer initialized for topic: %s", topic);
    }

    public static void send(String message) {
        if (!initialized.get() || closed.get()) {
            log.warn("KafkaProducer not available — event skipped");
            return;
        }
        try {
            //producer.send(new ProducerRecord<>(topic, message));
            producer.send(new ProducerRecord<>(topic, message),
                    (metadata, exception) -> {
                if (exception != null) {
                    log.error("Kafka send failed", exception);
                } else {
                    log.debugf("Kafka event sent to %s@%d offset=%d",
                            metadata.topic(), metadata.partition(), metadata.offset());
                }
            });
        } catch (Exception e) {
            log.error("Failed to send event to Kafka", e);
        }
    }

    public static void close() {
        //producer.close();
        if (closed.getAndSet(true)) {
            return; // already closed
        }
        if (producer != null) {
            try {
                producer.flush();
                producer.close();
                log.info("✅ KafkaProducer closed cleanly");
            } catch (Exception e) {
                log.error("Error closing KafkaProducer", e);
            }
        }
    }
}
