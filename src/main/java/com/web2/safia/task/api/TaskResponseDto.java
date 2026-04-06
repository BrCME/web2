package com.web2.safia.task.api;

import java.time.LocalDateTime;
import java.util.UUID;

import com.web2.safia.employee.api.BriefEmployeeResponseDto;
import com.web2.safia.project.api.BriefProjectResponseDto;
import com.web2.safia.task.internal.Task;

public record TaskResponseDto(
		UUID id,
		BriefEmployeeResponseDto creator,
		LocalDateTime createdAt,
		LocalDateTime updatedAt,
		LocalDateTime deletedAt,
		String name,
		String description,
		String status,
		BriefProjectResponseDto project,
		LocalDateTime deadLine) {

	public TaskResponseDto(Task task) {
		this(
				task.getId(),
				new BriefEmployeeResponseDto(task.getCreator()),
				task.getCreatedAt(),
				task.getUpdatedAt(),
				task.getDeletedAt(),
				task.getName(),
				task.getDescription(),
				task.getStatus().name(),
				new BriefProjectResponseDto(task.getProject()),
				task.getDeadLine());
	}
}
