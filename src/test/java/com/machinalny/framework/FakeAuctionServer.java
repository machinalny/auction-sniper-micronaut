package com.machinalny.framework;

import io.micronaut.context.annotation.Prototype;
import jakarta.inject.Inject;
import net.bytebuddy.utility.RandomString;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Prototype
public class FakeAuctionServer {

    @Inject
    private AuctionServerListener auctionServerListener;

    private Set<String> auctions = new HashSet<>();


    public String startSellingRandomItem() {
        String item = RandomString.make(12);
        auctions.add(item);
        return item;
    }

    public void hasReceivedJoinRequestFromSniper() throws InterruptedException {
        boolean messageConsumed = this.auctionServerListener.getLatch().await(20, TimeUnit.SECONDS);
        if (!messageConsumed) {
            throw new RuntimeException("Didn't got any message");
        } else {
            System.out.println(this.auctionServerListener.getPayload());
        }

    }

    public void announceClosed(String itemName) {

    }
}
