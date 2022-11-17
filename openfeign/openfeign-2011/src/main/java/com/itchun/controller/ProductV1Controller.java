package com.itchun.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductV1Controller {

    @RequestMapping(value = "/find_success.do")
    public Object find_success(@RequestParam(name = "name") String name) {
        return "请求成功，2011客户端返回:" + name;
    }

    @RequestMapping(value = "/find_fail.do")
    public Object find_fail() {
        throw new RuntimeException("请求失败");
    }
}
