package com.itchun;

import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * springboot 使用默认 redis配置
 */
@SpringBootTest
public class RedissonTest {

    @Resource
    private RedissonClient redissonClient;

    @Test
    void string() {
        RLock rLock = redissonClient.getLock("123");
        rLock.lock(5, TimeUnit.SECONDS);

    }
}
