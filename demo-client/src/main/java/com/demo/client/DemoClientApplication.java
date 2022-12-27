package com.demo.client;

import com.demo.service.EnableDemoService;
import com.ulisesbocchio.jasyptspringboot.environment.StandardEncryptableEnvironment;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@RefreshScope
@EnableAsync
@EnableDemoService
public class DemoClientApplication {


    public static void main(String[] args) {
        new SpringApplicationBuilder().environment(new StandardEncryptableEnvironment())
                .sources(DemoClientApplication.class).run(args);
    }
}
