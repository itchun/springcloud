package com.itchun.feignClient;

import com.itchun.util.FailUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// contextId 用于区别相同的 value 或者 name
@FeignClient(contextId = "openfeign-2011-2", value = "openfeign-client-group-1", fallback = FailUtil.class)
public interface ProductFailFeign {

    @RequestMapping(value = "find_fail.do")
    String find_fail(@RequestParam("name") String name);
}
