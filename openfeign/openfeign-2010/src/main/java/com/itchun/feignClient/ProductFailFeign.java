package com.itchun.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// contextId 用于区别相同的 value 或者 name
@FeignClient(contextId = "openfeign-2011-2", value = "openfeign-client-group-1")
public interface ProductFailFeign {


    // 简单失败请求
    @RequestMapping(value = "find_fail.do", produces = "application/json;charset=utf-8")
    String find_fail(@RequestParam("name") String name);


    // 超时间请求
    @RequestMapping(value = "find_timeout.do", produces = "application/json;charset=utf-8")
    String find_timeout(@RequestParam("name") String name);

}
