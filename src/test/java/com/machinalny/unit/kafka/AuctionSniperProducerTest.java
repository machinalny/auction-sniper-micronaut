package com.machinalny.unit.kafka;

import com.machinalny.framework.FakeAuctionServer;
import com.machinalny.kafka.AuctionSniperProducer;
import io.micronaut.context.ApplicationContext;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

@MicronautTest
class AuctionSniperProducerTest {

    @Inject
    FakeAuctionServer auctionServer;

    @Test
    void auctionSender() {
        Map<String, Object> config = Collections.singletonMap(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092"
        );

        try (ApplicationContext ctx = ApplicationContext.run(config)) {
            AuctionSniperProducer auctionSniperProducer = ctx.getBean(AuctionSniperProducer.class);
            auctionSniperProducer.send("Plate", "Plate");
        }
    }

    @Test
    void testConsumer() throws InterruptedException {
        auctionServer.hasReceivedJoinRequestFromSniper();
    }
}
