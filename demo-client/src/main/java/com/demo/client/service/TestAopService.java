package com.demo.client.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component("TestAopService")
@Scope("prototype")
@Slf4j
public class TestAopService {
    private String name;

    TestAopService(String name) {
        this.name = name;
    }

    public String getTestApo() {
        return "hello " + name;
    }
}
