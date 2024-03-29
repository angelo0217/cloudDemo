package com.demo.client.integration;

import feign.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DemoClientBasicConfiguration {

    @Bean
    public Request.Options options() {
        return new Request.Options(
                30000,
                30000
        );
    }

    @Bean
    public FeignErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }
}
