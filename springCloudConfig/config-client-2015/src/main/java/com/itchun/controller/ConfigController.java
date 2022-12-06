package com.itchun.controller;

import com.itchun.config.YmlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {

    @Autowired
    private YmlConfig ymlConfig;

    @RequestMapping("map")
    private Object getParam() {
        return ymlConfig.getParam();
    }

    @RequestMapping("data")
    private Object getData() {
        return ymlConfig.getData();
    }

    @Value("${itchun.name}")
    private String name;


    @RequestMapping("name")
    private Object name() {
        return name;
    }
}
