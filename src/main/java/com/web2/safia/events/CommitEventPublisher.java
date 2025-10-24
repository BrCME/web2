package com.web2.safia.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.web2.safia.models.CommitType;
import com.web2.safia.models.Employee;

@Component
public class CommitEventPublisher {
	private final ApplicationEventPublisher applicationEventPublisher;

	public CommitEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	public void publishUpdateCommitEvent(final String description, final Employee creator) {
		var commitEvent = new CommitEvent(this, description, CommitType.ATUALIZACAO, creator);
		applicationEventPublisher.publishEvent(commitEvent);
	}

	public void publishActivateCommitEvent(final String description, final Employee creator) {
		var commitEvent = new CommitEvent(this, description, CommitType.ATIVACAO, creator);
		applicationEventPublisher.publishEvent(commitEvent);
	}

	public void publishCreateCommitEvent(final String description, final Employee creator) {
		var commitEvent = new CommitEvent(this, description, CommitType.CRIACAO, creator);
		applicationEventPublisher.publishEvent(commitEvent);
	}

	public void publishDeactivateCommitEvent(final String description, final Employee creator) {
		var commitEvent = new CommitEvent(this, description, CommitType.DESATIVACAO, creator);
		applicationEventPublisher.publishEvent(commitEvent);
	}

	public void publishRemoveCommitEvent(final String description, final Employee creator) {
		var commitEvent = new CommitEvent(this, description, CommitType.REMOCAO, creator);
		applicationEventPublisher.publishEvent(commitEvent);
	}
}
