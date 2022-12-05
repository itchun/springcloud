package com.itchun.多态的使用;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class A {

    @Autowired
    private B<CA> ia;

    @RequestMapping("get.do")
    public void a() {
        System.out.println(ia.getName());
    }
}
