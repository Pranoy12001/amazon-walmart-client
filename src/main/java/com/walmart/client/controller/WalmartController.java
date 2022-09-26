package com.walmart.client.controller;

import com.walmart.client.payload.response.TokenViewModel;
import com.walmart.client.webclient.WalmartRestConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/walmart")
public class WalmartController {
    private final WalmartRestConsumer walmartRestConsumer;


    @Autowired
    public WalmartController(WalmartRestConsumer walmartRestConsumer) {
        this.walmartRestConsumer = walmartRestConsumer;
    }

    @GetMapping("/token")
    public ResponseEntity<TokenViewModel> getToken() {
        return new ResponseEntity<>(walmartRestConsumer.consumeWalmartTokenApi(), HttpStatus.OK);
    }
}
