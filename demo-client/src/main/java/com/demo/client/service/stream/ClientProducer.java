package com.demo.client.service.stream;

import com.demo.client.model.DemoClientStreamVo;
import com.demo.service.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Slf4j
@Service
public class ClientProducer {
    
//    @Autowired
//    @Output(DemoClientOutputProtocol.OUT_PUT)
//    private MessageChannel demoClientChannel;
//
//    public void send(DemoClientStreamVo demoClientStreamVo){
//        try {
//            log.debug(">>>>> send message stream :{}", JsonUtil.objectToJson(demoClientStreamVo));
//            demoClientChannel.send(MessageBuilder.withPayload(demoClientStreamVo)
//                    .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());
//        } catch (Exception ex){
//            log.error("send message stream error {}", JsonUtil.objectToJson(demoClientStreamVo), ex);
//        }
//    }

    @Resource
    private StreamBridge streamBridge;

    public void send(DemoClientStreamVo demoClientStreamVo) {
        System.out.println("~~~~~~~~~~~~~~~~~~~");
        streamBridge.send("client-out-0", JsonUtil.objectToJson(demoClientStreamVo));
    }
}
