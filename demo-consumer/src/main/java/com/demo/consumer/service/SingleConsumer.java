package com.demo.consumer.service;


import com.demo.service.model.DemoClientStreamVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;


@Slf4j
@Service
public class SingleConsumer {

    private TestService testService;

    public SingleConsumer(TestService testService){
        this.testService = testService;
    }


//    @Bean
//    public Consumer<String> msg(){
//        return System.out::println;
//    }

    @Bean
    public Consumer<DemoClientStreamVo> msg() {
        return new Consumer<DemoClientStreamVo>() {
            @Override
            public void accept(DemoClientStreamVo demoClientStreamVo) {
                System.out.println(demoClientStreamVo.getWord());
                testService.executeMsg(demoClientStreamVo);
            }
        };
    }
}
