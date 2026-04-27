package com.web2.safia.team.api.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.web2.safia.employee.api.dto.BriefEmployeeResponseDto;
import com.web2.safia.project.api.dto.BriefProjectResponseDto;
import com.web2.safia.shared.entity.Team;

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
						.map(BriefEmployeeResponseDto::new)
						.toList(),
				team.getAllProjects()
						.stream()
						.map(BriefProjectResponseDto::new)
						.toList());
	}
}
