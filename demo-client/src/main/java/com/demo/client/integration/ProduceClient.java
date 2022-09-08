package com.demo.client.integration;

import com.demo.service.model.ProduceRes;
import com.demo.service.model.TestModel;
import com.demo.service.protocol.TestProtocol;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "demo-consumer", path = "/demo_consumer", configuration = DemoClientBasicConfiguration.class)
public interface ProduceClient extends TestProtocol {

    default ProduceRes<TestModel> fallback(final java.lang.Throwable throwable) {
        TestModel testModel = new TestModel();
        testModel.setName("~~~~~");
        return new ProduceRes(testModel);
    }
}
