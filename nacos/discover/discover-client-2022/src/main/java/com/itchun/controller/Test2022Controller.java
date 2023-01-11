package com.itchun.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class Test2022Controller {

    @RequestMapping(value = "/getServiceName.do")
    public Object getServiceName(@RequestParam("name") String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("name2", name);
        return JSON.toJSON(map);
    }

}
