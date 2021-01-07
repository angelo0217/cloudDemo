package com.demo.client.config.async;

import com.demo.service.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(CustomAsyncExceptionHandler.class);

    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {

        logger.error("Method name -  {}, {}" , method.getName(), JsonUtil.objectToJson(obj));
        logger.error("Exception message -  {}" , throwable.getLocalizedMessage());
        logger.error("Exception message -  {}" , throwable.getMessage());
        logger.error("Exception message -  {}" , throwable.getCause());
    }

}