package com.demo.client.controller;

import com.demo.client.model.DemoClientStreamVo;
import com.demo.client.service.integration.TestService;
import com.demo.client.service.stream.ClientProducer;
import com.demo.client.service.stream.SingleProducer;
import com.demo.service.model.SingleModel;
import com.demo.service.model.TestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private ClientProducer clientProducer;

    @Autowired
    private TestService testService;

    @Autowired
    private SingleProducer singleProducer;

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/send")
    public String send(){
        SingleModel singleModel = new SingleModel();
        singleModel.setData("from client");
        singleProducer.send(singleModel);
        return "ok";
    }

    @GetMapping("/single")
    public String single(){
        DemoClientStreamVo demoClientStreamVo = new DemoClientStreamVo();
        demoClientStreamVo.setWord("1234");

        clientProducer.send(demoClientStreamVo);
        return "ok";
    }

    @GetMapping("/model")
    public TestModel getProvideModel(){
        return testService.getProduceTest();
    }
}
