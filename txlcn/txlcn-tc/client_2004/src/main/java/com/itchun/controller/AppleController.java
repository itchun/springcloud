package com.itchun.controller;

import com.itchun.service.AppleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppleController {

    @Autowired
    private AppleService appleService;

    @RequestMapping("save.do")
    private Object insert() {
        return appleService.insert();
    }

    @RequestMapping("exception/save.do")
    private Object insert_exception_1() {
        return appleService.insert_exception_1();
    }


}
