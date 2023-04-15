package com.machinalny.framework;

import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;
import jakarta.inject.Singleton;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.concurrent.CountDownLatch;

@Singleton
@KafkaListener(
        groupId = "auctionService",
        offsetReset = OffsetReset.EARLIEST,
        pollTimeout = "500ms"
)
public class AuctionServerListener {
    private CountDownLatch latch = new CountDownLatch(1);

    private String payload;

    @Topic("auction-service")
    public void receive(ConsumerRecord<String, String> consumerRecord) {
        payload = consumerRecord.value();
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public String getPayload() {
        return payload;
    }

    public void resetLatch(){
        this.latch = new CountDownLatch(1);
    }
}
