import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.demo.client.Const;
import com.demo.client.DemoClientApplication;
import com.demo.client.model.UserVo;
import com.demo.client.service.redis_stream.RedisStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoClientApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class RedisStreamTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisStream redisStream;

    @SuppressWarnings("unchecked")

    @Test
    public void test_ack() {
        StreamOperations<String, String, String> streamOperations = this.redisTemplate.opsForStream();

        // 获取my_group中的pending消息信息，本质上就是执行XPENDING指令
        PendingMessagesSummary pendingMessagesSummary = streamOperations.pending(Const.STREAM_KEY, "group-b");

        // 所有pending消息的数量
        long totalPendingMessages = pendingMessagesSummary.getTotalPendingMessages();

        if(totalPendingMessages > 0) {

            StreamReadOptions streamReadOptions = StreamReadOptions.empty()
                    // 如果没有数据，则阻塞1s 阻塞时间需要小于`spring.redis.timeout`配置的时间
                    .block(Duration.ofMillis(1000))
                    // 一直阻塞直到获取数据，可能会报超时异常
                    // .block(Duration.ofMillis(0))
                    // 1次获取10个数据
                    .count(10);

            System.out.println(totalPendingMessages);

            // 消费组名称
            String groupName = pendingMessagesSummary.getGroupName();

            // pending队列中的最小ID
            String minMessageId = pendingMessagesSummary.minMessageId();

            // pending队列中的最大ID
            String maxMessageId = pendingMessagesSummary.maxMessageId();

            log.info("消费组：{}，一共有{}条pending消息，最大ID={}，最小ID={}", groupName, totalPendingMessages, minMessageId, maxMessageId);


            // 每个消费者的pending消息数量
            Map<String, Long> pendingMessagesPerConsumer = pendingMessagesSummary.getPendingMessagesPerConsumer();

            pendingMessagesPerConsumer.entrySet().forEach(entry -> {

                // 消费者
                String consumer = entry.getKey();
                // 消费者的pending消息数量
                long consumerTotalPendingMessages = entry.getValue();

                log.info("消费者：{}，一共有{}条pending消息", consumer, consumerTotalPendingMessages);

                if (consumerTotalPendingMessages > 0) {
                    // 读取消费者pending队列的前10条记录，从ID=0的记录开始，一直到ID最大值
                    PendingMessages pendingMessages = streamOperations.pending(Const.STREAM_KEY, Consumer.from(groupName, consumer), Range.closed("0", "+"), 10);

                    // 遍历所有Opending消息的详情
                    pendingMessages.forEach(message -> {
                        // 消息的ID
                        RecordId recordId = message.getId();
                        // 消息从消费组中获取，到此刻的时间
                        Duration elapsedTimeSinceLastDelivery = message.getElapsedTimeSinceLastDelivery();
                        // 消息被获取的次数
                        long deliveryCount = message.getTotalDeliveryCount();

                        log.info("openg消息，id={}, elapsedTimeSinceLastDelivery={}, deliveryCount={}", recordId, elapsedTimeSinceLastDelivery, deliveryCount);

                        if (consumer.equals("name1")) {
                            // 通过streamOperations，直接读取这条pending消息，
                            Range.Bound searchId = Range.Bound.inclusive(recordId.toString());
//                            List<MapRecord<String, String, String>> result = streamOperations.range(Const.STREAM_KEY, Range.of(searchId, searchId));
                            List<ObjectRecord<String, UserVo>> result = streamOperations.range(UserVo.class, Const.STREAM_KEY, Range.of(searchId, searchId));
//                            List<ObjectRecord<String, UserVo>> result = streamOperations
//                                    .read(UserVo.class, streamReadOptions, StreamOffset.create(Const.STREAM_KEY, ReadOffset.from(recordId.toString())));
                            // 开始和结束都是同一个ID，所以结果只有一条
                            System.out.println("=============" + result.size());

                            ObjectRecord<String, UserVo> objectRecord = result.get(0);

                            // 这里执行日志输出，模拟的就是消费逻辑
                            log.info("消费了pending消息：id={}, value={}", objectRecord.getId(), objectRecord.getValue());

                            if(objectRecord.getValue() instanceof UserVo){
                                System.out.println("is user vo ****");
                            } else {
                                System.out.println("===================================================");
                            }

                            // 如果手动消费成功后，往消费组提交消息的ACK
                            Long retVal = streamOperations.acknowledge("group-b", objectRecord);
                            log.info("消息ack，一共ack了{}条", retVal);
                        }
                    });
                }
            });
        }
    }

    @Test
    public void test() {

        StreamOperations<String, String, String> streamOperations = this.redisTemplate.opsForStream();

        StreamReadOptions streamReadOptions = StreamReadOptions.empty()
                // 如果没有数据，则阻塞1s 阻塞时间需要小于`spring.redis.timeout`配置的时间
                .block(Duration.ofMillis(1000))
                // 一直阻塞直到获取数据，可能会报超时异常
                // .block(Duration.ofMillis(0))
                // 1次获取10个数据
                .count(10);
        // 從消費者的pending佇列中讀取訊息
//        List<MapRecord<String, String, String>>  retVal = streamOperations.read(Consumer.from("group-b","name1"), StreamOffset.create(Const.STREAM_KEY, ReadOffset.from("0")));
        List<ObjectRecord<String, UserVo>> objectRecords = streamOperations
                .read(UserVo.class, streamReadOptions, StreamOffset.create(Const.STREAM_KEY, ReadOffset.from("0")));
        // 遍歷訊息
        for (ObjectRecord<String, UserVo> record : objectRecords ) {
            // 消費訊息
            log.info("訊息id={}, 訊息value={}", record.getId(), record.getValue());
            // 手動ack訊息
            if(record.getValue() instanceof UserVo){
                log.info(" is user vo");
            }
//            streamOperations.acknowledge("group-b", record);
            redisTemplate.opsForStream().acknowledge(record.getStream(),"group-b", record.getId());
        }
    }


    @Test
    public void testCheck(){
        checkGroup(Const.STREAM_KEY, "group-b");
    }

    private void checkGroup(String key, String group) {
        // 创建需要校验的分组List
        List<String> consumers = new ArrayList<>();
        consumers.add(group);
        StreamInfo.XInfoConsumers infoGroups = null;
        try {
            // 获取Stream的所有组信息
            infoGroups = redisStream.consumers(key, group);
        } catch (RedisSystemException | InvalidDataAccessApiUsageException ex) {
            log.error("group key not exist or commend error", ex);
        }

        // 遍历校验分组是否存在
        for (String consumer : consumers) {
            boolean consumerExist = Objects.nonNull(infoGroups);

            System.out.println(consumer);
            // 创建不存在的分组
            if (!consumerExist) {
//                redisStream.createGroup(key, consumer);
                System.out.println("~~~~~~~~~~~~~~");
            }
        }
    }
}