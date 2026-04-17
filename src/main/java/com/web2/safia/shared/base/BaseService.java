package com.web2.safia.shared.base;

import org.springframework.context.ApplicationEventPublisher;

public abstract class BaseService {
	protected final ApplicationEventPublisher eventPublisher;

	public BaseService(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}
}
