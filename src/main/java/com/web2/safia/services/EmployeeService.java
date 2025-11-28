package com.web2.safia.services;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import com.web2.safia.repositories.adapters.JpaProjectRepository;
import com.web2.safia.repositories.adapters.JpaTaskRepository;
import com.web2.safia.repositories.adapters.JpaTeamRepository;
import com.web2.safia.repositories.adapters.JpaWorkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web2.safia.exceptions.DomainException;
import com.web2.safia.models.Employee;
import com.web2.safia.models.Project;
import com.web2.safia.models.Task;
import com.web2.safia.models.Team;
import com.web2.safia.models.Work;
import com.web2.safia.repositories.adapters.JpaEmployeeRepository;

@Service
public class EmployeeService {
	private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

	private final JpaTaskRepository taskRepository;
	private final JpaWorkRepository workRepository;
	private final JpaTeamRepository teamRepository;
	private final JpaProjectRepository projectRepository;
	private final JpaEmployeeRepository employeeRepository;

	public EmployeeService(
			JpaEmployeeRepository employeeRepository,
			JpaProjectRepository projectRepository,
			JpaTeamRepository teamRepository,
			JpaWorkRepository workRepository,
			JpaTaskRepository taskRepository) {

		this.employeeRepository = employeeRepository;
		this.projectRepository = projectRepository;
		this.teamRepository = teamRepository;
		this.workRepository = workRepository;
		this.taskRepository = taskRepository;
	}

	public Page<Employee> getAll(Pageable pageable) {
		return employeeRepository.findAll(pageable);
	}

	public Employee getById(UUID id) throws DomainException {
		Optional<Employee> employee = employeeRepository.findById(id);

		if (!employee.isPresent()) {
			throw new DomainException("Usuário não existe");
		}

		return employee.get();
	}

	public Employee getByEmail(String email) throws DomainException {
		Optional<Employee> employee = employeeRepository.findByEmail(email);

		if (!employee.isPresent()) {
			logger.error("Usuário com email '{}' não existe", email);
			throw new DomainException("Usuário não existe");
		}

		return employee.get();
	}

	public Set<Employee> getAllByCreator(Employee creator) {
		return employeeRepository.findAllByCreator(creator.getId());
	}

	public Set<Project> getAllProjects(Employee employee) {
		return projectRepository.findByUserId(employee.getId());
	}

	public Set<Team> getAllTeams(Employee employee) {
		return teamRepository.findByUserId(employee.getId());
	}

	public Set<Work> getAllWorks(Employee employee) {
		return workRepository.findByUserId(employee.getId());
	}

	public Set<Task> getAllTasks(Employee employee) {
		return taskRepository.findByUserId(employee.getId());
	}
}
