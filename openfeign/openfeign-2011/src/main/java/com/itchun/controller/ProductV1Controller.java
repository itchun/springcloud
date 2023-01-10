package com.itchun.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductV1Controller {

    @RequestMapping(value = "/find_success.do")
    public Object find_success() {
        return "请求成功";
    }


    @RequestMapping(value = "/find_balancer.do")
    public Object find_balancer(@RequestParam(name = "name") String name) {
        return "请求成功，2011客户端返回:" + name;
    }


    @RequestMapping(value = "/find_fail.do")
    public Object find_fail() {
        throw new RuntimeException("请求失败");
    }


    @RequestMapping(value = "/find_timeout.do")
    public Object find_timeout(@RequestParam(name = "name") String name) throws Exception {
        System.out.println("请求到我了");
        Thread.sleep(20 * 1000);
        return "请求成功，2011客户端返回:" + name;
    }
}
