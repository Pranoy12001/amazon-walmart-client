package com.walmart.client.webclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.walmart.client.payload.response.TokenViewModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class WalmartRestConsumer {
    @Value("${walmart.token.uri}")
    private String tokenApiUri;

    @Value("${walmart.client.id}")
    private String clientId;

    @Value("${walmart.client.secret}")
    private String clientSecret;

    private final ObjectMapper mapper;


    public WalmartRestConsumer(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public TokenViewModel consumeWalmartTokenApi() {
        WebClient client = WebClient.create();
        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("WM_SVC.NAME", "client_credentials");

        WebClient.ResponseSpec responseSpec = client.post()
                .uri(tokenApiUri)
                .headers(httpHeaders -> {
                    httpHeaders.set("Authorization", prepareAuthHeader(clientId, clientSecret));
                    httpHeaders.set("WM_QOS.CORRELATION_ID", "1234");
                    httpHeaders.set("WM_SVC.NAME", "Walmart Marketplace");
                })
                .body(BodyInserters.fromValue(bodyMap))
                .retrieve();

        return responseSpec.bodyToMono(TokenViewModel.class).block();
    }

    private String prepareAuthHeader(String clientId, String clientSecret) {
        if(clientId == null || clientSecret == null) {
            throw new RuntimeException();
        }

        String creds = String.format("%s:%s", clientId, clientSecret);

        return "Basic " + Base64.getEncoder().encodeToString(creds.getBytes(StandardCharsets.UTF_8));
    }
}
