package com.web2.safia.shared.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

// @Configuration
// public class AsyncConfig implements AsyncConfigurer {
// 	@Bean
// 	@Override public Executor getAsyncExecutor() {
// 		var executor = new ThreadPoolTaskExecutor();
// 		executor.setCorePoolSize(10);
// 		executor.setMaxPoolSize(10);
// 		executor.setQueueCapacity(10_000);
// 		executor.setWaitForTasksToCompleteOnShutdown(true);
// 		executor.setAwaitTerminationSeconds(10);
// 		return executor;
// 	}
// }
