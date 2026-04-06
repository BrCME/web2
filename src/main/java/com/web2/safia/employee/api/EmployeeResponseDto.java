package com.web2.safia.employee.api;

import java.time.LocalDate;
import java.util.List;

import com.web2.safia.employee.internal.Employee;
import com.web2.safia.project.internal.Project;
import com.web2.safia.task.internal.Task;
import com.web2.safia.team.internal.Team;
import com.web2.safia.work.internal.Work;

public record EmployeeResponseDto(
		String name,
		String email,
		String phoneNumber,
		String cpf,
		LocalDate birthDate,
		String status,
		List<Team> teams,
		List<Project> projects,
		List<Work> works,
		List<Task> tasks) {

	public EmployeeResponseDto(Employee employee) {
		this(
				employee.getName(),
				employee.getEmail(),
				employee.getPhoneNumber(),
				employee.getCpf(),
				employee.getBirthDate(),
				employee.getStatus().name(),
				List.copyOf(employee.getAllTeams()),
				List.copyOf(employee.getAllProjects()),
				List.copyOf(employee.getAllWorks()),
				List.copyOf(employee.getAllTasks()));
	}
}
