package com.demo.client.service.stream;


import com.demo.service.model.DemoClientStreamVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
public class ClientConsumer {

    @Bean
    public Consumer<DemoClientStreamVo> msg(){
        return demoClientStreamVo -> {
            System.out.println(demoClientStreamVo.getWord());
        };
    }

}
