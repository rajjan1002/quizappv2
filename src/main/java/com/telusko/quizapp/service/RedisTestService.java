package com.telusko.quizapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisTestService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void test() {

        redisTemplate.opsForValue()
                .set("hello", "Rajjan");

        System.out.println(
                redisTemplate.opsForValue().get("hello")
        );
    }
}