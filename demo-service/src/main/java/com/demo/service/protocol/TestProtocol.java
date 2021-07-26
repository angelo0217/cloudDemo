package com.demo.service.protocol;

import com.demo.service.model.ProduceRes;
import com.demo.service.model.TestModel;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.web.bind.annotation.GetMapping;


public interface TestProtocol {
    @CircuitBreaker(name = "backendA", fallbackMethod = "fallback")
    @GetMapping("/model")
    ProduceRes<TestModel> getTestModel();
}
