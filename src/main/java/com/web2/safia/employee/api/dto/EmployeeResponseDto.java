package com.web2.safia.employee.api.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.web2.safia.project.api.dto.ProjectResponseDto;
import com.web2.safia.shared.entity.Employee;
import com.web2.safia.task.api.dto.TaskResponseDto;
import com.web2.safia.team.api.dto.BriefTeamResponseDto;
import com.web2.safia.work.api.dto.BriefWorkResponseDto;

public record EmployeeResponseDto(
		UUID id,
		String name,
		String email,
		String phoneNumber,
		String cpf,
		LocalDate birthDate,
		String status,
		List<BriefTeamResponseDto> teams,
		List<ProjectResponseDto> projects,
		List<BriefWorkResponseDto> works,
		List<TaskResponseDto> tasks) {

	public EmployeeResponseDto(Employee employee) {
		this(
				employee.getId(),
				employee.getName(),
				employee.getEmail(),
				employee.getPhoneNumber(),
				employee.getCpf(),
				employee.getBirthDate(),
				employee.getStatus().name(),
				employee.getAllTeams().stream().map(BriefTeamResponseDto::new).toList(),
				employee.getAllProjects().stream().map(ProjectResponseDto::new).toList(),
				employee.getAllWorks().stream().map(BriefWorkResponseDto::new).toList(),
				employee.getAllTasks().stream().map(TaskResponseDto::new).toList());
	}
}
