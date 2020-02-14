package com.rest.recruit.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RedisZsetUtil {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //reverseRank
    public Long reverseRank(String key,String value){
        ZSetOperations<String,String> zSetOperations = redisTemplate.opsForZSet();
        return zSetOperations.reverseRank(key,value);

    }

    public void add(String key,String value, int score){
        ZSetOperations<String,String> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add(key,value,score);
    }

    public Set<ZSetOperations.TypedTuple<String>> reverseRangeWithScores(String key,int start,int end){
        ZSetOperations<String,String> zSetOperations = redisTemplate.opsForZSet();
        return zSetOperations.reverseRangeWithScores(key,start,end);
    }

    public void remove(String key,String value){
        ZSetOperations<String,String> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.remove(key,value);
    }

}
