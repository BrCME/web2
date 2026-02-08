package com.web2.safia.team.dtos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.web2.safia.employee.Employee;
import com.web2.safia.employee.dtos.BriefEmployeeResponseDto;
import com.web2.safia.project.dtos.BriefProjectResponseDto;
import com.web2.safia.team.Team;

public record BriefTeamResponseDto(
		UUID id,
		Employee creator,
		LocalDateTime createdAt,
		LocalDateTime updatedAt,
		LocalDateTime deletedAt,
		String name,
		String description,
		List<BriefEmployeeResponseDto> employees,
		List<BriefProjectResponseDto> projects) {

	public BriefTeamResponseDto(Team team) {
		this(
				team.getId(),
				team.getCreator(),
				team.getCreatedAt(),
				team.getUpdatedAt(),
				team.getDeletedAt(),
				team.getName(),
				team.getDescription(),
				team.getAllEmployees()
						.stream()
						.map(employee -> new BriefEmployeeResponseDto(employee))
						.toList(),
				team.getAllProjects()
						.stream()
						.map(project -> new BriefProjectResponseDto(project))
						.toList());
	}

	// public BriefTeamResponseDto(TeamResponseDto team) {

	// }
}
