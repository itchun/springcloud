package com.itchun.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// contextId 用于区别相同的 value 或者 name
@FeignClient(contextId = "openfeign-2011-1", value = "openfeign-client-group-1")
public interface ProductSuccessFeign {

    // 简单成功请求
    @RequestMapping(value = "find_success.do", produces = "application/json;charset=utf-8")
    String find_success(@RequestParam("name") String name);


    // 负载均衡请求
    @RequestMapping(value = "find_balancer.do", produces = "application/json;charset=utf-8")
    String find_balancer(@RequestParam("name") String name);



}
