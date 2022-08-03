package com.itchun;

import net.minidev.json.JSONUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * springboot 使用默认 redis配置
 */
@SpringBootTest
public class JedisTest {

    @Autowired
    private JedisPool jedisPool;

    @Test
    void string() {
        List<String> list = jedisPool.getResource().aclList();
        list.add("asd");
        list.add("1236000");
        System.out.println(list.size());
    }
}
