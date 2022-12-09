package com.itchun.controller;

import com.itchun.config.YmlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {

    @Autowired
    private YmlConfig ymlConfig;

    @RequestMapping("config")
    private Object name() {
        return ymlConfig;
    }
}
