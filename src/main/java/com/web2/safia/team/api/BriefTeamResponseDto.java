package com.web2.safia.team.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.web2.safia.employee.api.BriefEmployeeResponseDto;
import com.web2.safia.project.api.BriefProjectResponseDto;
import com.web2.safia.team.internal.Team;

public record BriefTeamResponseDto(
		UUID id,
		BriefEmployeeResponseDto creator,
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
				new BriefEmployeeResponseDto(team.getCreator()),
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
