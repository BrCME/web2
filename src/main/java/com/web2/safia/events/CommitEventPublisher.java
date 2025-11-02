package com.web2.safia.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.web2.safia.models.CommitType;
import com.web2.safia.models.Employee;

@Component
public class CommitEventPublisher {
	private ApplicationEventPublisher applicationEventPublisher;

	public CommitEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	private void publishCommitEvent(CommitEvent commitEvent) {
		applicationEventPublisher.publishEvent(commitEvent);
	}

	public void publishUpdateCommitEvent(String description, Employee creator) {
		publishCommitEvent(new CommitEvent(this, description, CommitType.ATUALIZACAO, creator));
	}

	public void publishActivateCommitEvent(String description, Employee creator) {
		publishCommitEvent(new CommitEvent(this, description, CommitType.ATIVACAO, creator));
	}

	public void publishCreateCommitEvent(String description, Employee creator) {
		publishCommitEvent(new CommitEvent(this, description, CommitType.CRIACAO, creator));
	}

	public void publishDeactivateCommitEvent(String description, Employee creator) {
		publishCommitEvent(new CommitEvent(this, description, CommitType.DESATIVACAO, creator));
	}

	public void publishRemoveCommitEvent(String description, Employee creator) {
		publishCommitEvent(new CommitEvent(this, description, CommitType.REMOCAO, creator));
	}
}
