package com.web2.safia.commit.api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.web2.safia.commit.api.CommitType;
import com.web2.safia.commit.internal.Commit;
import com.web2.safia.employee.api.dto.BriefEmployeeResponseDto;

public record CommitResponseDto(
		UUID id,
		BriefEmployeeResponseDto creator,
		LocalDateTime createdAt,
		LocalDateTime updatedAt,
		LocalDateTime deletedAt,
		String description,
		CommitType type) {

	public CommitResponseDto(Commit commit) {
		this(
				commit.getId(),
				new BriefEmployeeResponseDto(commit.getCreator()),
				commit.getCreatedAt(),
				commit.getUpdatedAt(),
				commit.getDeletedAt(),
				commit.getDescription(),
				commit.getType());
	}
}
