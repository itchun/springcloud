package com.itchun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/test")
    public String test() {
        String result = restTemplate.getForObject("http://eureka-client-2001/read", String.class);
        return result;
    }
}
