package com.demo.client.service.redis_stream;

import com.demo.client.Const;
import com.demo.service.model.StreamModel;
import com.demo.service.utils.RedisStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;

@Slf4j
@Configuration
public class ClientRedisStreamConfig {


    private ListenerMessage listenerMessage;
    private ListenerMessage2 listenerMessage2;
    private RedisStream redisStream;

    public ClientRedisStreamConfig(ListenerMessage listenerMessage, RedisStream redisStream, ListenerMessage2 listenerMessage2){
        this.listenerMessage = listenerMessage;
        this.redisStream = redisStream;
        this.listenerMessage2 = listenerMessage2;
    }


    @Bean
    public Subscription subscription(RedisConnectionFactory factory,
                                     StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, StreamModel>> streamMessageListenerContainerOptions){

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

    @Bean
    public Subscription subscription2(RedisConnectionFactory factory,
                                     StreamMessageListenerContainer.StreamMessageListenerContainerOptions streamMessageListenerContainerOptions){

        StreamMessageListenerContainer listenerContainer = StreamMessageListenerContainer.create(factory,
                streamMessageListenerContainerOptions);

        Subscription subscription = listenerContainer.receiveAutoAck(
                Consumer.from("group-b","name2"),
                StreamOffset.create(Const.STREAM_KEY, ReadOffset.lastConsumed()),
                listenerMessage2
        );

        listenerContainer.start();

        return subscription;
    }



}
