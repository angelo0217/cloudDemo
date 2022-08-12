package com.demo.client.service.redis_stream;

import com.demo.client.model.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ListenerMessage implements StreamListener<String, ObjectRecord<String, UserVo>> {

    private StringRedisTemplate redisTemplate;

    public ListenerMessage(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

//
//    @Override
//    public void onMessage(MapRecord<String, String, String> message) {
//        // 接收到消息
//        System.out.println("message id " + message.getId());
//        System.out.println("stream " + message.getStream());
//        System.out.println("body " + message.getValue());
//        System.out.println("body " + message.getValue().get("age"));
//
//        if(message.getValue() instanceof UserVo){
//            System.out.println("body is user");
//        }
////        redisTemplate.opsForStream().acknowledge(message.getStream(),"group-b", message.getId());
//    }

    @Override
    public void onMessage(ObjectRecord<String, UserVo> message) {
        // 接收到消息
        System.out.println("message id " + message.getId());
        System.out.println("stream " + message.getStream());
        System.out.println("body " + message.getValue());

        if(message.getValue() instanceof UserVo){
            System.out.println("body is user");
        }

//        redisTemplate.opsForStream().acknowledge(message.getStream(),"group-b", message.getId());
//        redisTemplate.opsForStream().delete(message.getStream(), message.getId());// delete id
    }
}
