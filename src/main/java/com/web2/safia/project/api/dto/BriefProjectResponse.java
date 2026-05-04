package com.web2.safia.project.api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.web2.safia.employee.api.dto.BriefEmployeeResponse;
import com.web2.safia.shared.entity.Project;

public record BriefProjectResponse(
		UUID id,
		BriefEmployeeResponse creator,
		LocalDateTime createdAt,
		String name,
		String description,
		BriefEmployeeResponse manager) {

	public BriefProjectResponse(Project project) {
		this(
				project.getId(),
				new BriefEmployeeResponse(project.getCreator()),
				project.getCreatedAt(),
				project.getName(),
				project.getDescription(),
				new BriefEmployeeResponse(project.getManager()));
	}

	public BriefProjectResponse(ProjectResponse project) {
		this(
				project.id(),
				project.creator(),
				project.createdAt(),
				project.name(),
				project.description(),
				project.manager());
	}
}
