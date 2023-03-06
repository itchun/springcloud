package com.itchun.string;

import com.alibaba.fastjson.JSONObject;

public class TestMain {

    public static void main(String[] args) {
        String aa = "aa";
        String bb = "aa";
        String str = "{\"id\":\"aa\"}";
        JSONObject json = JSONObject.parseObject(str);
        String cc = json.getString("id");
        System.out.println(aa == bb);
        System.out.println(aa == cc);
    }
}
