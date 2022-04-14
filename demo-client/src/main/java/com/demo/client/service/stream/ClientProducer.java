package com.demo.client.service.stream;

import com.demo.service.model.DemoClientStreamVo;
import com.demo.service.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ClientProducer {

    private StreamBridge streamBridge;

    private ClientProducer(StreamBridge streamBridge){
        this.streamBridge = streamBridge;
    }


    public void send(DemoClientStreamVo demoClientStreamVo) {
//        可用 sender-out-0 指定到設定党內的設定，或是直接送topic key: test-message
//         streamBridge.send("test-message", JsonUtil.objectToJson(demoClientStreamVo));
        streamBridge.send("sender-out-0", JsonUtil.objectToJson(demoClientStreamVo));
    }
}
