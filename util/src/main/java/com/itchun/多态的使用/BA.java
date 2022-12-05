package com.itchun.多态的使用;

import org.springframework.stereotype.Service;

@Service
public class BA implements B<CA> {

    @Override
    public String getName() {
        return "IAA";
    }
}
