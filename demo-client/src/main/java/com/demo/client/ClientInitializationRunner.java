package com.demo.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(1)
public class ClientInitializationRunner implements CommandLineRunner {
    @Override
    public void run(String... args) {
        log.info("~~~~~~~~~~~~~~ server start ~~~~~~~~~~~~~~~~");
    }
}
