package com.demo.provide.controller;

import com.demo.service.model.ProduceRes;
import com.demo.service.model.TestModel;
import com.demo.service.protocol.TestProtocol;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController implements TestProtocol {

    @Override
    public ProduceRes<TestModel> getTestModel() {
        TestModel testModel = new TestModel();
        testModel.setName("test name");
        return new ProduceRes(testModel);
    }
}
