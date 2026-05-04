package com.web2.safia.team.api.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.web2.safia.employee.api.dto.BriefEmployeeResponse;
import com.web2.safia.project.api.dto.BriefProjectResponse;
import com.web2.safia.shared.entity.Team;

public record BriefTeamResponse(
		UUID id,
		BriefEmployeeResponse creator,
		LocalDateTime createdAt,
		LocalDateTime updatedAt,
		LocalDateTime deletedAt,
		String name,
		String description,
		List<BriefEmployeeResponse> employees,
		List<BriefProjectResponse> projects) {

	public BriefTeamResponse(Team team) {
		this(
				team.getId(),
				new BriefEmployeeResponse(team.getCreator()),
				team.getCreatedAt(),
				team.getUpdatedAt(),
				team.getDeletedAt(),
				team.getName(),
				team.getDescription(),
				team.getAllEmployees()
						.stream()
						.map(BriefEmployeeResponse::new)
						.toList(),
				team.getAllProjects()
						.stream()
						.map(BriefProjectResponse::new)
						.toList());
	}
}
