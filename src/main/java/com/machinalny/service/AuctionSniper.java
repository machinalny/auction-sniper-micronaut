package com.machinalny.service;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.context.annotation.Prototype;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

@Prototype
public class AuctionSniper {

    private Producer<String, String> kafkaProducer;


    public AuctionSniper(@KafkaClient("auction-service") Producer<String, String> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    public void upsertActionOn(String auction) {
        kafkaProducer.send(new ProducerRecord<>("auction-service", auction, auction));
    }
}
