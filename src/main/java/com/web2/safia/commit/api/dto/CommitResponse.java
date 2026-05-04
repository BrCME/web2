package com.web2.safia.commit.api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.web2.safia.employee.api.dto.BriefEmployeeResponse;
import com.web2.safia.shared.entity.Commit;
import com.web2.safia.shared.entity.CommitType;

public record CommitResponse(
		UUID id,
		BriefEmployeeResponse creator,
		LocalDateTime createdAt,
		LocalDateTime updatedAt,
		LocalDateTime deletedAt,
		String description,
		CommitType type) {

	public CommitResponse(Commit commit) {
		this(
				commit.getId(),
				new BriefEmployeeResponse(commit.getCreator()),
				commit.getCreatedAt(),
				commit.getUpdatedAt(),
				commit.getDeletedAt(),
				commit.getDescription(),
				commit.getType());
	}
}
