package com.web2.safia.employee.api.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.web2.safia.project.api.dto.ProjectResponse;
import com.web2.safia.shared.entity.Employee;
import com.web2.safia.task.api.dto.TaskResponse;
import com.web2.safia.team.api.dto.BriefTeamResponse;
import com.web2.safia.work.api.dto.BriefWorkResponse;

public record EmployeeResponse(
		UUID id,
		String name,
		String email,
		String phoneNumber,
		String cpf,
		LocalDate birthDate,
		String status,
		List<BriefTeamResponse> teams,
		List<ProjectResponse> projects,
		List<BriefWorkResponse> works,
		List<TaskResponse> tasks) {

	public EmployeeResponse(Employee employee) {
		this(
				employee.getId(),
				employee.getName(),
				employee.getEmail(),
				employee.getPhoneNumber(),
				employee.getCpf(),
				employee.getBirthDate(),
				employee.getStatus().name(),
				employee.getAllTeams().stream().map(BriefTeamResponse::new).toList(),
				employee.getAllProjects().stream().map(ProjectResponse::new).toList(),
				employee.getAllWorks().stream().map(BriefWorkResponse::new).toList(),
				employee.getAllTasks().stream().map(TaskResponse::new).toList());
	}
}
