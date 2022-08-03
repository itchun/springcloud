package com.itchun;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * springboot 使用默认 redis配置
 */
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    void string() {
        redisTemplate.opsForValue().set("test","123");
        System.out.println(redisTemplate.opsForValue().get("test"));
        redisTemplate.delete("test");
        System.out.println(redisTemplate.opsForValue().get("test"));
    }

    @Test
    void list() {
        redisTemplate.opsForList().rightPush("test","1");
        redisTemplate.opsForList().rightPush("test","1");
        redisTemplate.opsForList().rightPush("test","1");
        System.out.println(redisTemplate.opsForList().range("test",0,1000));
        redisTemplate.delete("test");
        System.out.println(redisTemplate.opsForList().range("test",0,1000));
    }

}
