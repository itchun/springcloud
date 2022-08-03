package com.itchun.mian;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

public class WathDogMain {

    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.1.160:6379").setPassword("datahome123");
        config.setLockWatchdogTimeout(20000L); // 修改看门狗默认过期时间
        RedissonClient client = Redisson.create(config);
        RLock lock = client.getLock("wath:dog");
        try {
            lock.lock();
            TimeUnit.SECONDS.sleep(60);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
