package com.web2.safia.commit.api;

import java.time.LocalDateTime;
import java.util.UUID;

import com.web2.safia.commit.internal.Commit;
import com.web2.safia.employee.internal.Employee;

public record CommitResponseDto(
		UUID id,
		Employee creator,
		LocalDateTime createdAt,
		LocalDateTime updatedAt,
		LocalDateTime deletedAt,
		String description,
		CommitType type) {

	public CommitResponseDto(Commit commit) {
		this(
				commit.getId(),
				commit.getCreator(),
				commit.getCreatedAt(),
				commit.getUpdatedAt(),
				commit.getDeletedAt(),
				commit.getDescription(),
				commit.getType());
	}
}
