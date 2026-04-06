package com.web2.safia.project.api;

import java.time.LocalDateTime;
import java.util.UUID;

import com.web2.safia.employee.api.BriefEmployeeResponseDto;
import com.web2.safia.project.internal.Project;

public record BriefProjectResponseDto(
		UUID id,
		BriefEmployeeResponseDto creator,
		LocalDateTime createdAt,
		String name,
		String description,
		BriefEmployeeResponseDto manager) {

	public BriefProjectResponseDto(Project project) {
		this(
				project.getId(),
				new BriefEmployeeResponseDto(project.getCreator()),
				project.getCreatedAt(),
				project.getName(),
				project.getDescription(),
				new BriefEmployeeResponseDto(project.getManager()));
	}

	public BriefProjectResponseDto(ProjectResponseDto project) {
		this(
				project.id(),
				project.creator(),
				project.createdAt(),
				project.name(),
				project.description(),
				project.manager());
	}
}
