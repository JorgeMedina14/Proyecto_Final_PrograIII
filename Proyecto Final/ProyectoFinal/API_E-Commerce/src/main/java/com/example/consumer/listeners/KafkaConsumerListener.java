package com.example.consumer.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class KafkaConsumerListener {

    private Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerListener.class);

    @KafkaListener(topics = {"APINegocio-Topic"}, groupId = "group_id")
    public void listen(String message) {
        LOGGER.info("Mensaje recibido: " + message);
    }
}
