package com.demo.client.service.integration;

import com.demo.client.integration.ProduceClient;
import com.demo.service.model.TestModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestService {
    @Autowired
    private ProduceClient produceClient;

    public TestModel getProduceTest(){
        return produceClient.getTestModel().getData();
    }
}
