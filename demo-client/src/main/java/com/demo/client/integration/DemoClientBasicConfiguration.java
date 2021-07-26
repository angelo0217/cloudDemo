package com.demo.client.integration;

import com.demo.client.integration.fallback.ProduceClientFallBack;
import feign.Request;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DemoClientBasicConfiguration {
    private final CircuitBreakerRegistry registry;
    private final ProduceClientFallBack fallBack;

    @Bean
    public Request.Options options() {
        return new Request.Options(
                30000,
                30000
        );
    }

//    @Bean
//    public FeignErrorDecoder errorDecoder() {
//        return new FeignErrorDecoder();
//    }
}
