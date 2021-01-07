package com.demo.client.controller;

import com.demo.client.config.async.ScheduledFutureManager;
import com.demo.client.service.task.TestTask;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScheduledController {
    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private ScheduledFutureManager scheduledFutureManager;

    @GetMapping("/scheduled")
    public String Scheduled(){
        TestTask testTask = beanFactory.getBean(TestTask.class, "scheduled name");

        scheduledFutureManager.addTestFuture("key123", testTask);
        return "hello";
    }

    @GetMapping("/scheduled_fix")
    public String ScheduledFix(){
        TestTask testTask = beanFactory.getBean(TestTask.class, "scheduled name");

        scheduledFutureManager.addTestFix("key123", testTask);
        return "hello";
    }
}
