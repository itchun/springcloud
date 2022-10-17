package com.itchun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Test2000Controller {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/test/read/2001")
    public String read_2001() {
        String result = restTemplate.getForObject("http://eureka-client-2/test/write", String.class);
        return result;
    }

    @RequestMapping("/test/read/2002")
    public String read_2002() {
        String result = restTemplate.getForObject("http://eureka-client-2/test/write", String.class);
        return result;
    }

    @RequestMapping("/test/write")
    public String write() {
        return "请求到了，我是客户端2000";
    }
}
