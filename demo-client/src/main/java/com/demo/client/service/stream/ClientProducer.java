package com.demo.client.service.stream;

import com.demo.client.model.DemoClientStreamVo;
import com.demo.service.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Slf4j
@Service
public class ClientProducer {

    @Resource
    private StreamBridge streamBridge;

    public void send(DemoClientStreamVo demoClientStreamVo) {
//        可用 sender-out-0 指定到設定党內的設定，或是直接送topic key: test-message
//         streamBridge.send("test-message", JsonUtil.objectToJson(demoClientStreamVo));
        streamBridge.send("sender-out-0", JsonUtil.objectToJson(demoClientStreamVo));
    }
}
