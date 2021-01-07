package com.demo.service.config;

import com.demo.service.utils.JacksonSerializerUtil;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisTemplateConfig {
	
	@Bean("longRedisTemplate")
    public RedisTemplate<String, Long> redisTemplateLong(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Long> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(new GenericToStringSerializer<Long>(Long.class));
		template.setValueSerializer(new GenericToStringSerializer<Long>(Long.class));
		return template;
	}

	@Bean("stringRedisTemplate")
	public RedisTemplate<String, String> redisTemplateString(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, String> redis = new RedisTemplate<>();
		redis.setConnectionFactory(redisConnectionFactory);
		redis.afterPropertiesSet();
		return redis;
	}
	
	/** 
	 * Support Any type 
	 * 
	 * */
	@Bean("genericRedisTemplate")
	public <V> RedisTemplate<String, V> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, V> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(JacksonSerializerUtil.jackson2JsonRedisSerializer());
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}
	
}
