package com.demo.client.service.redis_stream;

import com.demo.client.Const;
import com.demo.client.model.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Configuration
public class RedisStreamConfig {


    private ListenerMessage listenerMessage;
    private RedisStream redisStream;

    public RedisStreamConfig(ListenerMessage listenerMessage, RedisStream redisStream){
        this.listenerMessage = listenerMessage;
        this.redisStream = redisStream;
    }


    @Bean
    public StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ?> streamMessageListenerContainerOptions(){
        int processors = Runtime.getRuntime().availableProcessors();
        AtomicInteger index = new AtomicInteger(1);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(processors, processors, 0, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(), r -> {
            Thread thread = new Thread(r);
            thread.setName("async-stream-consumer-" + index.getAndIncrement());
            thread.setDaemon(true);
            return thread;
        });

        return StreamMessageListenerContainer
                .StreamMessageListenerContainerOptions
                .builder()
                .batchSize(3)
                .executor(executor)
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


    public class CustomErrorHandler implements ErrorHandler {
        @Override
        public void handleError(Throwable t) {
            log.error("发生了异常", t);
        }
    }
}
