package com.itchun.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId = "open-client-01", value = "discover-client-2021")
public interface OpenFeginClient {

    @RequestMapping(value = "api/getServiceName.do", produces = "application/json;charset=utf-8")
    String get_service_name(@RequestParam("name") String name);
}
