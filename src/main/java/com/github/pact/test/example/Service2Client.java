package com.github.pact.test.example;

import org.springframework.web.client.RestTemplate;

public class Service2Client {

    private String baseUrl;

    public Service2Client(){};

    public Service2Client(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String callService2(String serviceEndPint) {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(baseUrl + serviceEndPint, String.class);
        return  response;
    }
}