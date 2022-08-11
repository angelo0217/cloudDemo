package com.demo.client.service.stream;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.cloud.stream.binder.rabbit.RabbitStreamMessageHandler;
import org.springframework.cloud.stream.config.ProducerMessageHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.SuccessCallback;

import java.util.function.Consumer;

@Slf4j
@EnableRabbit
@Component
public class MqHandler {


//    @Bean
//    public Consumer<String> demo_ack(){
//        return demoClientStreamVo -> {
//            System.out.println(demoClientStreamVo);
//        };
//    }
//    @Bean
//    ProducerMessageHandlerCustomizer<MessageHandler> demo_ack() {
//        return (hand, dest) -> {
//            RabbitStreamMessageHandler handler = (RabbitStreamMessageHandler) hand;
//            handler.setConfirmTimeout(5000);
//            handler.setSuccessCallback(
//                    new SuccessCallback() {
//                        @Override
//                        public void onSuccess(Object result) {
//                            log.info("~~~~~~~~~~~~{}", result);
//                        }
//                    }
//            );
//        };
//    }
}
