package com.web2.safia.employee.api;

import java.time.LocalDate;
import java.util.List;

import com.web2.safia.employee.internal.Employee;
import com.web2.safia.project.api.ProjectResponseDto;
import com.web2.safia.task.api.TaskResponseDto;
import com.web2.safia.team.api.BriefTeamResponseDto;
import com.web2.safia.work.api.BriefWorkResponseDto;

public record EmployeeResponseDto(
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
				employee.getName(),
				employee.getEmail(),
				employee.getPhoneNumber(),
				employee.getCpf(),
				employee.getBirthDate(),
				employee.getStatus().name(),
				employee.getAllTeams().stream().map(team -> new BriefTeamResponseDto(team)).toList(),
				employee.getAllProjects().stream().map(project -> new ProjectResponseDto(project)).toList(),
				employee.getAllWorks().stream().map(work -> new BriefWorkResponseDto(work)).toList(),
				employee.getAllTasks().stream().map(task -> new TaskResponseDto(task)).toList());
	}
}
