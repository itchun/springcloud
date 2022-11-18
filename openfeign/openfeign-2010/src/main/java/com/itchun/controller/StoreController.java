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

    // 简单成功请求
    @RequestMapping(value = "/find_success.do")
    public Object find_success(@RequestParam("name") String name) {
        return productSuccessFeign.find_success(name);
    }


    // 负载均衡请求
    @RequestMapping(value = "/find_balancer.do")
    public Object find_balancer(@RequestParam("name") String name) {
        return productSuccessFeign.find_balancer(name);
    }


    // 简单失败请求
    @RequestMapping(value = "/find_fail.do")
    public Object find_fail(@RequestParam("name") String name) {
        return productFailFeign.find_fail(name);
    }


    // 超时间请求
    @RequestMapping(value = "/find_timeout.do")
    public Object find_timeout(@RequestParam("name") String name) {
        return productFailFeign.find_timeout(name);
    }


    // 超时重试请求
    @RequestMapping(value = "/find_timeout_retry.do")
    public Object find_timeout_retry(@RequestParam("name") String name) {
        return productFailFeign.find_timeout(name);
    }
}
