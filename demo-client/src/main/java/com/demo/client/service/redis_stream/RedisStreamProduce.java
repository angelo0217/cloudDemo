package com.demo.client.service.redis_stream;

import com.demo.client.Const;
import com.demo.service.model.SingleModel;
import com.demo.service.model.StreamModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisStreamProduce {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisStreamProduce(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void sendRecord(String test_word) {
        var single = new SingleModel();
        single.setData("test");

        var streamModel = new StreamModel(test_word, 10, single);

        ObjectRecord<String, ?> record = StreamRecords.newRecord()
                .in(Const.STREAM_KEY)
                .ofObject(streamModel)
                .withId(RecordId.autoGenerate());

        RecordId recordId = redisTemplate.opsForStream()
                .add(record);

        log.info("返回的record-id:[{}]", recordId);
    }
}
