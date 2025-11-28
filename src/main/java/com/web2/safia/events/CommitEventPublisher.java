package com.web2.safia.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.web2.safia.models.Commit;
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
		publishCommitEvent(new CommitEvent(this, description, Commit.Type.ATUALIZACAO, creator));
	}

	public void publishActivateCommitEvent(String description, Employee creator) {
		publishCommitEvent(new CommitEvent(this, description, Commit.Type.ATIVACAO, creator));
	}

	public void publishCreateCommitEvent(String description, Employee creator) {
		publishCommitEvent(new CommitEvent(this, description, Commit.Type.CRIACAO, creator));
	}

	public void publishDeactivateCommitEvent(String description, Employee creator) {
		publishCommitEvent(new CommitEvent(this, description, Commit.Type.DESATIVACAO, creator));
	}

	public void publishRemoveCommitEvent(String description, Employee creator) {
		publishCommitEvent(new CommitEvent(this, description, Commit.Type.REMOCAO, creator));
	}
}
