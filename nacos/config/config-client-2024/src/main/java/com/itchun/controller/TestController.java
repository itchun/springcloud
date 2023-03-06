package com.itchun.controller;

import com.itchun.config.MyData;
import com.itchun.dataSource.BeanInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RefreshScope
public class TestController {

    @Autowired
    private BeanInit beanInit;

    @RequestMapping(value = "/finds.do")
    public Object finds() throws Exception {
        return beanInit.init();
    }

    @RequestMapping(value = "/finds2.do")
    public Object finds2() throws Exception {
        return beanInit.init2();
    }

    @Autowired
    private MyData myData;

    @RequestMapping(value = "/dataSource.do")
    public Object dataSource() {
        return myData.getInfo();
    }
}
