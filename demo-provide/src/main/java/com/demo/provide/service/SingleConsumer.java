package com.demo.provide.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;


@Slf4j
@Service
public class SingleConsumer {

    @Bean
    public Consumer<String> msg(){
        return System.out::println;
    }
}
