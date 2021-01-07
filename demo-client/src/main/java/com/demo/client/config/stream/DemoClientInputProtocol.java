package com.demo.client.config.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface DemoClientInputProtocol {
    String IN_PUT = "demo_client_input";

    @Input(IN_PUT)
    SubscribableChannel subscriber();
}
