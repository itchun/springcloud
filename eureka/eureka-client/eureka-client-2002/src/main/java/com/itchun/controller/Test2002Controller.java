package com.itchun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Test2002Controller {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/test/write")
    public String test() {
        return "请求到了，我是2002";
    }
}
