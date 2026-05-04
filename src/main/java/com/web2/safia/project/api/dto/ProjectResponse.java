package com.web2.safia.project.api.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.web2.safia.employee.api.dto.BriefEmployeeResponse;
import com.web2.safia.shared.entity.Project;
import com.web2.safia.task.api.dto.BriefTaskResponse;
import com.web2.safia.team.api.dto.BriefTeamResponse;

public record ProjectResponse(
		UUID id,
		BriefEmployeeResponse creator,
		LocalDateTime createdAt,
		LocalDateTime updatedAt,
		LocalDateTime deletedAt,
		String name,
		String description,
		BriefTeamResponse team,
		BriefEmployeeResponse manager,
		List<BriefEmployeeResponse> employees,
		List<BriefTaskResponse> tasks) {

	public ProjectResponse(Project project) {
		this(
				project.getId(),
				new BriefEmployeeResponse(project.getCreator()),
				project.getCreatedAt(),
				project.getUpdatedAt(),
				project.getDeletedAt(),
				project.getName(),
				project.getDescription(),
				new BriefTeamResponse(project.getTeam()),
				new BriefEmployeeResponse(project.getManager()),
				project.getAllEmployees()
						.stream()
						.map(BriefEmployeeResponse::new)
						.toList(),
				project.getAllTasks()
						.stream()
						.map(BriefTaskResponse::new)
						.toList());
	}
}
