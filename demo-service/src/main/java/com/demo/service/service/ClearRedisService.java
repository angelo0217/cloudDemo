package com.demo.service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
public class ClearRedisService {

    private static final String REMOVE_DATE_TIME = "REMOVE_DATE_TIME";

    @Autowired
    @Qualifier("stringRedisTemplate")
    private RedisTemplate<String, String> stringRedisTemplate;

    @Autowired
    @Qualifier("longRedisTemplate")
    private RedisTemplate<String, Long> longRedisTemplate;

    public void clear(String mainKey){
        log.info("[server init] remove redis key {}", mainKey);
        if(mainKey.contains("*")) {
            Set<String> redisKeys = stringRedisTemplate.keys(mainKey);
            redisKeys.forEach(key -> stringRedisTemplate.delete(key));
        } else {
            stringRedisTemplate.delete(mainKey);
        }
    }

    public Long getLastTime(){
        return longRedisTemplate.opsForValue().get(REMOVE_DATE_TIME);
    }

    public void updateRemoveTime(){
        longRedisTemplate.opsForValue().set(REMOVE_DATE_TIME, System.currentTimeMillis());
    }
}
