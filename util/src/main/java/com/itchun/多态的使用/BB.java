package com.itchun.多态的使用;

import org.springframework.stereotype.Service;

@Service
public class BB implements B<CB> {

    @Override
    public String getName() {
        return "IAB";
    }
}
