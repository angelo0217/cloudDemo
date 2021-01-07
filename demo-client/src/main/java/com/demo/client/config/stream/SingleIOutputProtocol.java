package com.demo.client.config.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface SingleIOutputProtocol {
    String OUT_PUT = "single_client_output";

    @Output(OUT_PUT)
    MessageChannel publisher();
}
