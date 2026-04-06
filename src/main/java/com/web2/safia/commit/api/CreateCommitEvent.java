package com.web2.safia.commit.api;

import java.time.LocalDateTime;

import com.web2.safia.employee.internal.Employee;

public record CreateCommitEvent(
		String description,
		CommitType type,
		Employee creator,
		LocalDateTime createdAt) {

	public CreateCommitEvent(String description, CommitType type, Employee creator) {
		this(description, type, creator, LocalDateTime.now());
	}
}
