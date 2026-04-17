package com.web2.safia.shared.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
public class EventConfig {
	@Bean
	ApplicationEventMulticaster applicationEventMulticaster() {
		var applicationEventMulticaster = new SimpleApplicationEventMulticaster();
		applicationEventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());

		return applicationEventMulticaster;
	}
}
