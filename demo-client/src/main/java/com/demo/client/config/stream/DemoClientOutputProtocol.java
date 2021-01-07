package com.demo.client.config.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface DemoClientOutputProtocol {
    String OUT_PUT = "demo_client_output";

    @Output(OUT_PUT)
    MessageChannel publisher();
}
