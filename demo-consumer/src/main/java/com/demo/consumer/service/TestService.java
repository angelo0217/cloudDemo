package com.demo.consumer.service;

import com.demo.service.model.DemoClientStreamVo;
import com.demo.service.utils.JsonUtil;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    public void executeMsg(DemoClientStreamVo demoClientStreamVo) {
        System.out.println("test service: " + JsonUtil.objectToJson(demoClientStreamVo));
    }
}
