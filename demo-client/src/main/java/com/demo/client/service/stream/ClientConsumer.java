package com.demo.client.service.stream;


import com.demo.client.model.DemoClientStreamVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;


@Slf4j
@Service
public class ClientConsumer {

    @Bean
    public Consumer<DemoClientStreamVo> client_in() {
        return str -> {
            log.info("~~~~~~~~~~~~~~" + str);
        };
    }
}
