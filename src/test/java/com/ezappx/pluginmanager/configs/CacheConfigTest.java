package com.ezappx.pluginmanager.configs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheConfigTest {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Test
    public void redisTests() {
        redisTemplate.opsForValue().set("name", "test name");
        Object obj = redisTemplate.opsForValue().get("name");
        System.out.println(obj);
    }
}