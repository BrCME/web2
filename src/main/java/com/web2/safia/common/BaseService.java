package com.web2.safia.common;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public abstract class BaseService {
	protected final ApplicationEventPublisher eventPublisher;

	public BaseService(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}
}
