package com.demo.client.service.redis_stream;

import com.demo.client.Const;
import com.demo.client.model.UserVo;
import io.lettuce.core.RedisBusyException;
import io.lettuce.core.RedisCommandExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;
import org.springframework.util.ErrorHandler;

import java.time.Duration;
import java.util.Collections;

@Slf4j
@Configuration
public class RedisStreamConfig {


    private ListenerMessage listenerMessage;
    private RedisTemplate redisTemplate;

    public RedisStreamConfig(ListenerMessage listenerMessage, RedisTemplate redisTemplate){
        this.listenerMessage = listenerMessage;
        this.redisTemplate = redisTemplate;
    }


    @Bean
    public StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ?> streamMessageListenerContainerOptions(){
        return StreamMessageListenerContainer
                .StreamMessageListenerContainerOptions
                .builder()
                .pollTimeout(Duration.ofSeconds(1))
                .keySerializer(RedisSerializer.string())
                // 可以理解为 Stream 后方的字段的 key 的序列化方式
                .hashKeySerializer(RedisSerializer.string())
                // 可以理解为 Stream 后方的字段的 value 的序列化方式
                .hashValueSerializer(RedisSerializer.string())
                .objectMapper(new ObjectHashMapper())
                .errorHandler(new CustomErrorHandler())
                .targetType(UserVo.class)
                .build();
    }


    @Bean
    public StreamMessageListenerContainer streamMessageListenerContainer(RedisConnectionFactory factory,
                                                                         StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ?> streamMessageListenerContainerOptions){
        StreamMessageListenerContainer listenerContainer = StreamMessageListenerContainer.create(factory,
                streamMessageListenerContainerOptions);
        listenerContainer.start();
        return listenerContainer;
    }

    @Bean
    public Subscription subscription(StreamMessageListenerContainer streamMessageListenerContainer){
        createConsumerGroup(Const.STREAM_KEY, "group-b", redisTemplate);
        Subscription subscription = streamMessageListenerContainer.receiveAutoAck(
                Consumer.from("group-b","name1"),
                StreamOffset.create(Const.STREAM_KEY, ReadOffset.lastConsumed()),
                listenerMessage
        );

        return subscription;
    }

//    @Bean
//    public Subscription subscription(StreamMessageListenerContainer streamMessageListenerContainer){
//        createConsumerGroup(Const.STREAM_KEY, "group-b", redisTemplate);
//        Subscription subscription = streamMessageListenerContainer.receive(
//                Consumer.from("group-b","name1"),
//                StreamOffset.create(Const.STREAM_KEY, ReadOffset.lastConsumed()),
//                listenerMessage
//        );
//        return subscription;
//    }


    private void createConsumerGroup(String key, String group, RedisTemplate redisTemplate) {
        try {
            redisTemplate.opsForStream().createGroup(key, group);
        } catch (RedisSystemException e) {
            if (e.getRootCause().getClass().equals(RedisBusyException.class)) {
                log.info("STREAM - Redis group already exists, skipping Redis group creation: {}", group);
            } else if (e.getRootCause().getClass().equals(RedisCommandExecutionException.class)) {
                log.info("STREAM - Stream does not yet exist, creating empty stream: event-stream");
                // TODO: There has to be a better way to create a stream than this!?
                redisTemplate.opsForStream().add(key, Collections.singletonMap("", ""));
                redisTemplate.opsForStream().createGroup(key, group);
            } else throw e;
        }
    }

    public class CustomErrorHandler implements ErrorHandler {
        @Override
        public void handleError(Throwable t) {
            log.error("发生了异常", t);
        }
    }
}
