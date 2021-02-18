package com.demo.client;

import com.demo.client.config.stream.DemoClientInputProtocol;
import com.demo.client.config.stream.DemoClientOutputProtocol;
import com.demo.client.config.stream.SingleIOutputProtocol;
import com.ulisesbocchio.jasyptspringboot.environment.StandardEncryptableEnvironment;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@RefreshScope
@EnableAsync
@EnableSwagger2
@EnableBinding(value = {DemoClientInputProtocol.class, DemoClientOutputProtocol.class, SingleIOutputProtocol.class})
public class DemoClientApplication {


	public static void main(String[] args) {
		new SpringApplicationBuilder().environment(new StandardEncryptableEnvironment())
				.sources(DemoClientApplication.class).run(args);
	}
}
