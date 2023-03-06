package com.itchun.controller;

import com.itchun.config.Baby;
import com.itchun.config.Common;
import com.itchun.config.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RefreshScope
public class TestController {

    @Autowired
    private Baby baby; // 会自动刷新，不需要单独@RefreshScope

    @Value("${baby.name}")
    private String name; // 没有@RefreshScope的情况下，不会自动刷新，加上才会自动刷新

    @RequestMapping(value = "/info.do")
    public Object info() {
        return baby.getInfo();
    }


    @RequestMapping(value = "/name.do")
    public Object name() {
        return name;
    }

    @Autowired
    private Common common;

    @Autowired
    private DataSource dataSource;

    @RequestMapping(value = "/common.do")
    public Object common() {
        return common.getInfo();
    }

    @RequestMapping(value = "/dataSource.do")
    public Object dataSource() {
        return dataSource.getInfo();
    }
}
