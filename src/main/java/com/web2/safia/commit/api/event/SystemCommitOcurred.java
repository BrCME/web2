package com.web2.safia.commit.api.event;

import java.time.LocalDateTime;

import com.web2.safia.shared.entity.CommitType;
import com.web2.safia.shared.entity.Employee;

public record SystemCommitOcurred(
		String description,
		CommitType type,
		Employee creator,
		LocalDateTime createdAt) {

	public SystemCommitOcurred(String description, CommitType type, Employee creator) {
		this(description, type, creator, LocalDateTime.now());
	}
}
