package com.demo.client.integration;

import com.demo.service.model.ProduceRes;
import com.demo.service.model.TestModel;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name = "demo-provide", path = "/demo_provide", configuration = DemoClientBasicConfiguration.class)
public interface ProduceClient {

    @CircuitBreaker(name = "backendA", fallbackMethod = "fallback")
    @GetMapping({"/model"})
    ProduceRes<TestModel> getTestModel();

    default ProduceRes<TestModel> fallback(final java.lang.Throwable throwable) {
        TestModel testModel = new TestModel();
        testModel.setName("~~~~~");
        return new ProduceRes(testModel);
    }
}
