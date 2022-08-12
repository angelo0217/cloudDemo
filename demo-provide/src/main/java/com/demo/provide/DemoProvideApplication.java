package com.demo.provide;

import com.demo.service.EnableDemoService;
import org.springframework.boot.SpringApplication;
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
public class DemoProvideApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoProvideApplication.class, args);
	}
}
