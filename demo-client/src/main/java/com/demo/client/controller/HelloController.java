package com.demo.client.controller;

import com.demo.client.service.TestAopService;
import com.demo.client.service.redis_stream.RedisStreamProduce;
import com.demo.service.model.DemoClientStreamVo;
import com.demo.client.service.integration.TestService;
import com.demo.client.service.stream.ClientProducer;
import com.demo.service.model.SingleModel;
import com.demo.service.model.TestModel;
import com.demo.service.service.HelloService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    private ClientProducer clientProducer;
    private TestService testService;
    private HelloService helloService;
    private RedisStreamProduce redisStreamProduce;
    private BeanFactory beanFactory;

    public HelloController(ClientProducer clientProducer, TestService testService, HelloService helloService,
                           RedisStreamProduce redisStreamProduce, BeanFactory beanFactory){
        this.clientProducer = clientProducer;
        this.testService = testService;
        this.helloService = helloService;
        this.redisStreamProduce = redisStreamProduce;
        this.beanFactory = beanFactory;
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

    @GetMapping("/send_redis")
    public String sendRedisStream() {
        redisStreamProduce.sendRecord("12344445");
        return "success";
    }

    @GetMapping("/test_aop")
    public String testAop() {
        return testService.getTestApo();
    }

    @GetMapping("/test_aop2")
    public String testAop2() {
        var testService2 = (TestAopService) beanFactory.getBean("TestAopService", "Dean");
        return testService2.getTestApo();
    }
}
