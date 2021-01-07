package com.demo.client.service.stream;

import com.demo.client.config.stream.SingleIOutputProtocol;
import com.demo.service.model.SingleModel;
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
public class SingleProducer {
    
    @Autowired
    @Output(SingleIOutputProtocol.OUT_PUT)
    private MessageChannel demoClientChannel;

    public void send(SingleModel singleModel){
        try {
            log.debug(">>>>> send message stream :{}", JsonUtil.objectToJson(singleModel));
            demoClientChannel.send(MessageBuilder.withPayload(singleModel)
                    .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());
        } catch (Exception ex){
            log.error("send message stream error {}", JsonUtil.objectToJson(singleModel), ex);
        }
    }


}
