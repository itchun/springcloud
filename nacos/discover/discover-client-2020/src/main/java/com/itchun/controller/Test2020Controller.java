package com.itchun.controller;

import com.itchun.openfeign.OpenFeginClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api")
public class Test2020Controller {

    @Resource
    private OpenFeginClient client2021;

    @RequestMapping(value = "/get.do")
    public Object get_service_name(@RequestParam("name") String name) {
        return client2021.get_service_name(name);
    }

}
