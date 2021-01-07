package com.demo.client.config.async;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Component
public class ScheduledFutureManager {
    private Logger logger = LoggerFactory.getLogger(ScheduledFutureManager.class);
    private Map<String, ScheduledFuture> stringScheduledFutureMap = new ConcurrentHashMap<>();

    @Autowired
    @Qualifier("TestScheduled")
    private ScheduledExecutorService scheduledExecutorService;

    public void addTestFuture(String key, Runnable task){
        this.remove(key);
        logger.info("[ScheduledFutureManager](addTimeOutFuture) key: {}", key);
        stringScheduledFutureMap.put(key, scheduledExecutorService.schedule(task, 10, TimeUnit.SECONDS));
    }


    public void addTestFix(String key, Runnable task){
        this.remove(key);
        logger.info("[ScheduledFutureManager](addTimeOutFuture) key: {}", key);
        stringScheduledFutureMap.put(key, scheduledExecutorService.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS));
    }

    public void remove(String key){
        ScheduledFuture scheduledFuture = stringScheduledFutureMap.get(key);
        if(scheduledFuture != null){

            logger.debug("[ScheduledFutureManager](remove) key: {}", this.getClass().getSimpleName(), key);
            scheduledFuture.cancel(true);
            stringScheduledFutureMap.remove(key);
        }
    }
}
