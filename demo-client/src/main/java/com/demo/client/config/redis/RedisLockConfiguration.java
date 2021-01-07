package com.demo.client.config.redis;

import com.demo.service.config.RedisTemplateConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

@Configuration
@DependsOn({ "longRedisTemplate", "stringRedisTemplate", "genericRedisTemplate" })
@ComponentScan(basePackageClasses = { RedisTemplateConfig.class })
public class RedisLockConfiguration {

    @Bean
    public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
        return new RedisLockRegistry(redisConnectionFactory, "demo-lock", 10000);
    }

}
