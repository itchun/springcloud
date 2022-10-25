package com.itchun.controller;

import com.itchun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("save.do")
    private Object insert() {
        return userService.insert();
    }

    @RequestMapping("exception/save.do")
    private Object insert_exception_1() {
        return userService.insert_exception_1();
    }

    @RequestMapping("union/save.do")
    private Object insert_union_1() {
        return userService.insert_union_1();
    }

    @RequestMapping("union/exception/save1.do")
    private Object insert_union_exception_1() {
        return userService.insert_union_exception_1();
    }

    @RequestMapping("union/exception/save2.do")
    private Object insert_union_exception_2() {
        return userService.insert_union_exception_2();
    }

    @RequestMapping("union/exception/save3.do")
    private Object insert_union_exception_3() {
        return userService.insert_union_exception_3();
    }

    @RequestMapping("union/exception/save4.do")
    private Object insert_union_exception_4() {
        return userService.insert_union_exception_4();
    }
}
