package com.itchun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/test/write")
    public String write() {
        return "请求到了，我是客户端2001";
    }


    @RequestMapping("/test/read")
    public String read() {
        String result = restTemplate.getForObject("http://eureka-client-1/test/write", String.class);
        return result;
    }
}
