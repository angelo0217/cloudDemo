package com.demo.client.integration;

import com.demo.service.protocol.TestProtocol;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "demo-provide", path = "/demo_provide" , configuration = DemoClientBasicConfiguration.class)
public interface ProduceClient extends TestProtocol {
}
