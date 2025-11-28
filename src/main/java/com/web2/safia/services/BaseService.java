package com.web2.safia.services;

import org.springframework.stereotype.Service;

import com.web2.safia.events.CommitEventPublisher;

@Service
public abstract class BaseService {
	protected final CommitEventPublisher commitEventPublisher;

	public BaseService(CommitEventPublisher commitEventPublisher) {
		this.commitEventPublisher = commitEventPublisher;
	}
}
