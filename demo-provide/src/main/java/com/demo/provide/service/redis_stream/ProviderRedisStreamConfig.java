package com.demo.provide.service.redis_stream;

import com.demo.provide.Const;
import com.demo.service.utils.RedisStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;

@Slf4j
@Configuration
public class ProviderRedisStreamConfig {


    private ListenerMessage listenerMessage;
    private RedisStream redisStream;

    public ProviderRedisStreamConfig(ListenerMessage listenerMessage, RedisStream redisStream){
        this.listenerMessage = listenerMessage;
        this.redisStream = redisStream;
    }


    @Bean
    public Subscription subscription(RedisConnectionFactory factory,
                                     StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ?> streamMessageListenerContainerOptions){

        StreamMessageListenerContainer listenerContainer = StreamMessageListenerContainer.create(factory,
                streamMessageListenerContainerOptions);

        redisStream.createGroup(Const.STREAM_KEY, "group-b");

        Subscription subscription = listenerContainer.receiveAutoAck(
                Consumer.from("group-b","name1"),
                StreamOffset.create(Const.STREAM_KEY, ReadOffset.lastConsumed()),
                listenerMessage
        );

        listenerContainer.start();

        return subscription;
    }

}
