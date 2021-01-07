package com.demo.provide.service;


import com.demo.provide.config.SingleClientInputProtocol;
import com.demo.service.model.SingleModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class SingleConsumer {

    @StreamListener(SingleClientInputProtocol.IN_PUT)
    public void receive(@Payload SingleModel singleModel) {
        log.debug("stream get msg: {}", singleModel.getData());
    }
}
