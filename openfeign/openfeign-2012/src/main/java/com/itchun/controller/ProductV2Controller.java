package com.itchun.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductV2Controller {

    @RequestMapping(value = "/find_success.do")
    public Object find_success(@RequestParam(name = "name") String name) throws Exception {
        System.out.println("请求到我了");
        Thread.sleep(20 * 1000);
        return "请求成功，2012客户端返回:" + name;
    }

    @RequestMapping(value = "/find_fail.do")
    public Object find_fail() {
        throw new RuntimeException("请求失败");
    }
}
