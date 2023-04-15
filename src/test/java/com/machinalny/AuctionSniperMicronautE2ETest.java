package com.machinalny;

import com.machinalny.framework.FakeAuctionServer;
import com.machinalny.framework.TestContainersExtension;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@MicronautTest
@ExtendWith(TestContainersExtension.class)
class AuctionSniperMicronautE2ETest {

    @Inject
    EmbeddedApplication<?> application;

    @Inject
    @Client("/")
    HttpClient client;

    @Inject
    FakeAuctionServer auctionServer;

    @Test
    void sniperJoinsAuctionAndLoses() throws InterruptedException {
        // Item pops up on auction
        String itemName = auctionServer.startSellingRandomItem();

        // Sniper wants to start bidding
        String response = client.toBlocking().retrieve(HttpRequest.POST("/api/auction/", """
                {
                "auction": "%s"
                }
                """.formatted(itemName)));


        auctionServer.hasReceivedJoinRequestFromSniper();

        // Auction is closed and it is announced
        auctionServer.announceClosed(itemName);
        await().atMost(Duration.ofSeconds(20))
                .pollInterval(Duration.ofSeconds(3)).untilAsserted(() ->
                        assertThat(client.toBlocking().retrieve(HttpRequest.GET("/api/auction/" + itemName))).isEqualTo("FINISHED")

                );

    }

}
