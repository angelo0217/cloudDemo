package com.demo.client.service.stream;

import com.demo.client.config.stream.DemoClientOutputProtocol;
import com.demo.client.model.DemoClientStreamVo;
import com.demo.service.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;


@Slf4j
@Service
public class ClientProducer {
    
    @Autowired
    @Output(DemoClientOutputProtocol.OUT_PUT)
    private MessageChannel demoClientChannel;

    public void send(DemoClientStreamVo demoClientStreamVo){
        try {
            log.debug(">>>>> send message stream :{}", JsonUtil.objectToJson(demoClientStreamVo));
            demoClientChannel.send(MessageBuilder.withPayload(demoClientStreamVo)
                    .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());
        } catch (Exception ex){
            log.error("send message stream error {}", JsonUtil.objectToJson(demoClientStreamVo), ex);
        }
    }


}
