package com.demo.provide.controller;

import com.demo.service.model.ProduceRes;
import com.demo.service.model.TestModel;
import com.demo.service.protocol.TestProtocol;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController implements TestProtocol {

    private static boolean SWITCH = true;


    @GetMapping("/switch")
    public boolean switchOpen(){
        if(SWITCH){
            SWITCH = false;
        } else {
            SWITCH = true;
        }
        return SWITCH;
    }

    @Override
    public ProduceRes<TestModel> getTestModel() {
        if(SWITCH){
            throw new RuntimeException("error");
        }
        TestModel testModel = new TestModel();
        testModel.setName("test name");
        return new ProduceRes(testModel);
    }
}
