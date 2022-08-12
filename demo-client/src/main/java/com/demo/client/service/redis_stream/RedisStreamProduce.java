package com.demo.client.service.redis_stream;

import com.demo.client.Const;
import com.demo.client.model.UserVo;
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
        var userVo = new UserVo(test_word, 10);

        ObjectRecord<String, UserVo> record = StreamRecords.newRecord()
                .in(Const.STREAM_KEY)
                .ofObject(userVo)
                .withId(RecordId.autoGenerate());

        RecordId recordId = redisTemplate.opsForStream()
                .add(record);

        log.info("返回的record-id:[{}]", recordId);
    }
}
