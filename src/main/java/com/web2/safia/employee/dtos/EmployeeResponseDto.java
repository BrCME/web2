package com.web2.safia.employee.dtos;

import java.time.LocalDate;
import java.util.List;

import com.web2.safia.employee.Employee;
import com.web2.safia.project.Project;
import com.web2.safia.task.Task;
import com.web2.safia.team.Team;
import com.web2.safia.work.Work;

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
