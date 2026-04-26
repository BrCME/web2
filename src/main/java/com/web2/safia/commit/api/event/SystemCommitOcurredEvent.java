package com.web2.safia.commit.api.event;

import java.time.LocalDateTime;

import com.web2.safia.commit.api.CommitType;
import com.web2.safia.employee.internal.Employee;

public record SystemCommitOcurredEvent(
		String description,
		CommitType type,
		Employee creator,
		LocalDateTime createdAt) {

	public SystemCommitOcurredEvent(String description, CommitType type, Employee creator) {
		this(description, type, creator, LocalDateTime.now());
	}
}
