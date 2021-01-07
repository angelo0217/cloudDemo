package com.demo.client.config.async;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class AsyncFactory implements AsyncConfigurer {

	private static final ThreadGroup TEST_GROUP = new ThreadGroup("TestGroup");

	@Bean("TestAsync")
	public Executor createFeignClientAsync() {
		return setExecutor(30, 10000, 0, "test-async");
	}

	@Bean("TestScheduled")
	public ScheduledExecutorService timeOutScheduled() {
		return Executors.newScheduledThreadPool(10000, new ScheduleThreadFactory("test-scheduled", TEST_GROUP));
	}

	/**
	 *
	 * @param pool    預設線程數目
	 * @param maxPool 最大線程數目 (queue吃滿後才會產生新的線程)
	 * @param queue   容許排隊數目
	 * @param name    線程名稱
	 * @return
	 */
	public ThreadPoolTaskExecutor setExecutor(int pool, int maxPool, int queue, String name) {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(pool);
		executor.setMaxPoolSize(maxPool);
		executor.setQueueCapacity(queue);
		executor.setKeepAliveSeconds(60);
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setAwaitTerminationSeconds(5);
		executor.setThreadNamePrefix(name);
		return executor;
	}
	
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new CustomAsyncExceptionHandler();
    }
}
