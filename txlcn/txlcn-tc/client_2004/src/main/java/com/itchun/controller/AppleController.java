package com.itchun.controller;

import com.itchun.service.AppleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;

@RestController
public class AppleController {

    @Autowired
    private AppleService appleService;

    @RequestMapping("save.do")
    private Object insert(HttpServletRequest request) {
        Iterator<String> heards = request.getHeaderNames().asIterator();
        while (heards.hasNext()) {
            String heard = heards.next();
            System.out.println(heard + " : " + request.getHeader(heard));
        }

//        示例:
//        accept : text/plain, application/json, application/*+json, */*
//                x-group-id : 1b65f869f01a537
//        x-app-map : e30=
//                user-agent : Java/17.0.3.1
//        host : itchun:2004
//        connection : keep-alive
        return appleService.insert();
    }

    @RequestMapping("exception/save.do")
    private Object insert_exception_1(HttpServletRequest request) {
        Iterator<String> heards = request.getHeaderNames().asIterator();
        while (heards.hasNext()) {
            String heard = heards.next();
            System.out.println(heard + " : " + request.getHeader(heard));
        }
        return appleService.insert_exception_1();
    }


}
