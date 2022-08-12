package com.demo.service.config;

import com.demo.service.model.StreamModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.util.ErrorHandler;

import java.time.Duration;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Configuration
public class RedisStreamConfig {
    @Bean
    public StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, StreamModel>> streamMessageListenerContainerOptions(){
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
                .targetType(StreamModel.class)
                .build();
    }

    public class CustomErrorHandler implements ErrorHandler {
        @Override
        public void handleError(Throwable t) {
            log.error("发生了异常", t);
        }
    }
}
