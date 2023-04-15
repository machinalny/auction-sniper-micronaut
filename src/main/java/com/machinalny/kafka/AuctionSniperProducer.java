package com.machinalny.kafka;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient(
        id = "auction-service"
)
public interface AuctionSniperProducer {

    @Topic("auction-service")
    void send(@KafkaKey String key, String message);
}
