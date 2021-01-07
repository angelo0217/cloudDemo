package com.demo.provide;

import com.demo.provide.config.SingleClientInputProtocol;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@RefreshScope
@EnableAsync
@EnableBinding(value = {SingleClientInputProtocol.class})
public class DemoProvideApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder().sources(DemoProvideApplication.class).run(args);
	}
}
