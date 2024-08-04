package com.learning.UrlShrinker.services;

import com.fasterxml.jackson.databind.util.ClassUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.beans.JavaBean;
import java.time.Duration;
import java.time.temporal.TemporalUnit;

@Service
public class RedisService {
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    @Value("${url.expiration.seconds}")
    private Integer timeToExpireInSeconds;
    
    public void setValue(String key, String value){
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(timeToExpireInSeconds));
    }
    
    public String getValue(String key){
        return redisTemplate.opsForValue().get(key);
    }
}
