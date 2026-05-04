package com.web2.safia.task.api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.web2.safia.employee.api.dto.BriefEmployeeResponse;
import com.web2.safia.project.api.dto.BriefProjectResponse;
import com.web2.safia.shared.entity.Task;

public record BriefTaskResponse(
		UUID id,
		BriefEmployeeResponse creator,
		LocalDateTime createdAt,
		String name,
		String description,
		String status,
		BriefProjectResponse project,
		LocalDateTime deadLine) {

	public BriefTaskResponse(Task task) {
		this(
				task.getId(),
				new BriefEmployeeResponse(task.getCreator()),
				task.getCreatedAt(),
				task.getName(),
				task.getDescription(),
				task.getStatus().name(),
				new BriefProjectResponse(task.getProject()),
				task.getDeadLine());
	}

	public BriefTaskResponse(TaskResponse task) {
		this(
				task.id(),
				task.creator(),
				task.createdAt(),
				task.name(),
				task.description(),
				task.status(),
				task.project(),
				task.deadLine());
	}
}
