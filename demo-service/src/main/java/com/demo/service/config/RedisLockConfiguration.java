package com.demo.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

@Configuration
@DependsOn({ "longRedisTemplate", "stringRedisTemplate", "genericRedisTemplate" })
public class RedisLockConfiguration {

    @Value("${lock.redis.second}")
    private int lockSecond;

    @Bean
    public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
        return new RedisLockRegistry(redisConnectionFactory, "demo-lock", lockSecond * 1000);
    }

}
