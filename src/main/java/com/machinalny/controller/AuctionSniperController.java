package com.machinalny.controller;

import com.machinalny.service.AuctionSniper;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

import java.util.Map;

@Controller("/api/auction/")
public class AuctionSniperController {

    @Inject
    private AuctionSniper auctionSniper;

    @Post(produces = MediaType.TEXT_PLAIN)
    public String upsertAuctionOn(@Body Map<String, String> body){
        auctionSniper.upsertActionOn(body.get("auction"));
        return "Hello World";
    }

    @Get("/{itemName}")
    public String  getAuctionStatusByItemIdentification(@PathVariable String itemName)  {

        return "FINISHED";

    }
}
