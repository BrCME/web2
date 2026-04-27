package com.web2.safia.project.api.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.web2.safia.employee.api.dto.BriefEmployeeResponseDto;
import com.web2.safia.shared.entity.Project;
import com.web2.safia.task.api.dto.BriefTaskResponseDto;
import com.web2.safia.team.api.dto.BriefTeamResponseDto;

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
						.map(BriefEmployeeResponseDto::new)
						.toList(),
				project.getAllTasks()
						.stream()
						.map(BriefTaskResponseDto::new)
						.toList());
	}
}
