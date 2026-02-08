package com.web2.safia.project.dtos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.web2.safia.employee.dtos.BriefEmployeeResponseDto;
import com.web2.safia.project.Project;
import com.web2.safia.task.dtos.BriefTaskResponseDto;
import com.web2.safia.team.dtos.BriefTeamResponseDto;

public record ProjectResponseDto(
		UUID id,
		BriefEmployeeResponseDto creator,
		LocalDateTime createdAt,
		LocalDateTime updatedAt,
		LocalDateTime deletedAt,
		String name,
		String description,
		BriefTeamResponseDto team,
		BriefEmployeeResponseDto manager,
		List<BriefEmployeeResponseDto> employees,
		List<BriefTaskResponseDto> tasks) {

	public ProjectResponseDto(Project project) {
		this(
				project.getId(),
				new BriefEmployeeResponseDto(project.getCreator()),
				project.getCreatedAt(),
				project.getUpdatedAt(),
				project.getDeletedAt(),
				project.getName(),
				project.getDescription(),
				new BriefTeamResponseDto(project.getTeam()),
				new BriefEmployeeResponseDto(project.getManager()),
				project.getAllEmployees()
						.stream()
						.map(employee -> new BriefEmployeeResponseDto(employee))
						.toList(),
				project.getAllTasks()
						.stream()
						.map(task -> new BriefTaskResponseDto(task))
						.toList());
	}
}
