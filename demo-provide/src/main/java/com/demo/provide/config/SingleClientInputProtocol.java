package com.demo.provide.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface SingleClientInputProtocol {
    String IN_PUT = "single_client_input";

    @Input(IN_PUT)
    MessageChannel publisher();
}
