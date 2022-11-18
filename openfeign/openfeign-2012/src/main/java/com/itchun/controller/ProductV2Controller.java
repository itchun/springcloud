package com.itchun.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductV2Controller {

    @RequestMapping(value = "/find_balancer.do")
    public Object find_balancer(@RequestParam(name = "name") String name) throws Exception {
        return "请求成功，2012客户端返回:" + name;
    }
}
