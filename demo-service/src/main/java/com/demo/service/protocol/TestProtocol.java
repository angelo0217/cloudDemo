package com.demo.service.protocol;

import com.demo.service.model.ProduceRes;
import com.demo.service.model.TestModel;
import org.springframework.web.bind.annotation.GetMapping;


public interface TestProtocol {
    @GetMapping("/model")
    ProduceRes<TestModel> getTestModel();
}
