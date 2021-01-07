package com.demo.client.service.stream;


import com.demo.client.config.stream.DemoClientInputProtocol;
import com.demo.client.model.DemoClientStreamVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ClientConsumer {

    @StreamListener(DemoClientInputProtocol.IN_PUT)
    public void receive(@Payload DemoClientStreamVo demoClientStreamVo) {
        log.debug("stream get msg: {}", demoClientStreamVo.getWord());
    }
}
