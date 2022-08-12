package com.demo.client.service.redis_stream;

import com.demo.service.model.StreamModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ListenerMessage2 implements StreamListener<String, ObjectRecord<String, StreamModel>> {

    private StringRedisTemplate redisTemplate;

    public ListenerMessage2(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Override
    public void onMessage(ObjectRecord<String, StreamModel> message) {
        // 接收到消息
        System.out.println("****message id " + message.getId());

    }
}
