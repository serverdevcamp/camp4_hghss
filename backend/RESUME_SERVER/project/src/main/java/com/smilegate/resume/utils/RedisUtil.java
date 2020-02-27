package com.smilegate.resume.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public Long reverseRank(String key, String value) {
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        return zSetOperations.reverseRank(key, value);
    }

    public void add(String key, String value, int score) {
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add(key, value, score);
    }

    public void increaseScore(String key, String value, int score) {
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.incrementScore(key, value, score);
    }

}
