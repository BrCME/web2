package com.web2.safia.commit.events;

import java.time.LocalDateTime;

import com.web2.safia.commit.Commit;
import com.web2.safia.employee.Employee;

public record CreateCommitEvent(
		String description,
		Commit.Type type,
		Employee creator,
		LocalDateTime createdAt) {

	public CreateCommitEvent(String description, Commit.Type type, Employee creator) {
		this(description, type, creator, LocalDateTime.now());
	}
}
