package com.demo.client.controller;

import com.demo.client.model.DemoClientStreamVo;
import com.demo.client.service.integration.TestService;
import com.demo.client.service.stream.ClientProducer;
import com.demo.client.service.stream.SingleProducer;
import com.demo.service.model.SingleModel;
import com.demo.service.model.TestModel;
import com.demo.service.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    private ClientProducer clientProducer;
    private TestService testService;
    private SingleProducer singleProducer;
    private HelloService helloService;

    public HelloController(ClientProducer clientProducer, TestService testService, SingleProducer singleProducer, HelloService helloService){
        this.clientProducer = clientProducer;
        this.testService = testService;
        this.singleProducer = singleProducer;
        this.helloService = helloService;
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/send")
    public String send(){
        DemoClientStreamVo demoClientStreamVo = new DemoClientStreamVo();
        demoClientStreamVo.setWord("1234");
        clientProducer.send(demoClientStreamVo);
        return "ok";
    }

    @GetMapping("/single")
    public String single(){
        SingleModel singleModel = new SingleModel();
        singleModel.setData("from client");

//        singleProducer.send(singleModel);
        return "ok";
    }

    @GetMapping("/model")
    public TestModel getProvideModel(){
        return testService.getProduceTest();
    }

    @GetMapping("/service")
    public String helloService(){
        return helloService.helloService();
    }
}
