package com.itchun.controller;

import com.itchun.feignClient.ProductFailFeign;
import com.itchun.feignClient.ProductSuccessFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class StoreController {

    @Autowired
    private ProductSuccessFeign productSuccessFeign;
    @Resource
    private ProductFailFeign productFailFeign;

    @RequestMapping(value = "/find_1.do")
    public Object find_success(@RequestParam("name") String name) {
        return productSuccessFeign.find_success(name);
    }

    @RequestMapping(value = "/find_2.do")
    public Object find_fail(@RequestParam("name") String name) {
        return productFailFeign.find_fail(name);
    }
}
