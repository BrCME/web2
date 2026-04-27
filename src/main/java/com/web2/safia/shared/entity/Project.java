package com.web2.safia.shared.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.web2.safia.project.api.dto.CreateProjectRequestDto;
import com.web2.safia.shared.base.BaseEntity;
import com.web2.safia.task.internal.TaskStatus;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "project")
public class Project extends BaseEntity {
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description", nullable = false)
	private String description;

	@ManyToOne
	@JoinColumn(name = "team_id")
	private Team team;

	@ManyToOne
	@JoinColumn(name = "manager_id")
	private Employee manager;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "employee_to_project", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "employee_id"))
	@ElementCollection
	private final Set<Employee> employees = new HashSet<>();

	@OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
	@ElementCollection
	private final Set<Task> tasks = new HashSet<>();

	public Project() {
		super();
	}

	public Project(UUID id) {
		super(id);
	}

	public Project(CreateProjectRequestDto requestDto) {
		super();
		this.name = requestDto.name();
		this.description = requestDto.description();
	}

	public String getName() {
		return name;
	}

	public void setName(@Valid @NotBlank(message = "Name cannot be blank") String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(@Valid @NotBlank(message = "Description cannot be blank") String description) {
		this.description = description;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(@Valid @NotNull(message = "Team cannot be null") Team team) {
		this.team = team;
	}

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}

	public Set<Employee> getAllEmployees() {
		return Set.copyOf(employees);
	}

	public boolean addEmployee(Employee employee) {
		return employees.add(employee);
	}

	public boolean removeEmployee(Employee employee) {
		return employees.remove(employee);
	}

	public Set<Task> getAllTasks() {
		return Set.copyOf(tasks);
	}

	public Set<Task> getAllTasks(String statusName) {
		return tasks
				.stream()
				.filter(task -> task.getStatus().equals(TaskStatus.valueOf(statusName)))
				.collect(Collectors.toSet());
	}

	public Set<Task> getAllTasks(TaskStatus status) {
		return tasks
				.stream()
				.filter(task -> task.getStatus().equals(status))
				.collect(Collectors.toSet());
	}

	public boolean addTask(Task task) {
		return tasks.add(task);
	}

	public boolean removeTask(Task task) {
		return tasks.remove(task);
	}

	@Override
	public String toString() {
		return "Project [id=" + id +
				", name=" + name +
				", creator=" + creator.getEmail() +
				", createdAt=" + createdAt +
				", description=" + description +
				", updatedAt=" + updatedAt +
				", team=" + team.getName() +
				", manager=" + manager.getEmail() +
				", employees=" + employees.size() +
				", deletedAt=" + deletedAt + "]";
	}
}
