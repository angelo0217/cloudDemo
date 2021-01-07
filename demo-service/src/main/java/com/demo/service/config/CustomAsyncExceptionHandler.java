package com.demo.service.config;

import com.demo.service.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

@Slf4j
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

	@Override
	public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
		log.error("Method name -  {}, {}" , method.getName(), JsonUtil.objectToJson(obj));
		log.error("local message -  {}" , throwable.getLocalizedMessage());
		log.error("message -  {}" , throwable.getMessage());
		log.error("cause -  {}" , throwable.getCause());
	}

}
