package com.itchun.util;

import com.itchun.feignClient.ProductFailFeign;
import org.springframework.stereotype.Component;

@Component
public class FailUtil implements ProductFailFeign {

    @Override
    public String find_fail(String name) {
        System.out.println("请求失败了：" + name);
        return null;
    }
}
