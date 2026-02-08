package com.web2.safia.task.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import com.web2.safia.employee.dtos.BriefEmployeeResponseDto;
import com.web2.safia.project.dtos.BriefProjectResponseDto;
import com.web2.safia.task.Task;

public record BriefTaskResponseDto(
		UUID id,
		BriefEmployeeResponseDto creator,
		LocalDateTime createdAt,
		String name,
		String description,
		String status,
		BriefProjectResponseDto project,
		LocalDateTime deadLine) {

	public BriefTaskResponseDto(Task task) {
		this(
				task.getId(),
				new BriefEmployeeResponseDto(task.getCreator()),
				task.getCreatedAt(),
				task.getName(),
				task.getDescription(),
				task.getStatus().name(),
				new BriefProjectResponseDto(task.getProject()),
				task.getDeadLine());
	}

	public BriefTaskResponseDto(TaskResponseDto task) {
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
