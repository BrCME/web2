package com.web2.safia.models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Task extends Base {
	@NotBlank(message = "Nome é obrigatório")
	@Column(name = "name", nullable = false)
	private String name;

	@NotBlank(message = "Descrição é obrigatória")
	@Column(name = "description", nullable = false)
	private String description;

	@Enumerated(EnumType.STRING)
	private TaskStatus status;

	@NotNull(message = "Projeto é obrigatório")
	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;

	@Future(message = "Prazo de entrega deve estar no futuro")
	@Column(name = "dead_line", nullable = false)
	private LocalDateTime deadLine;

	@OneToMany(mappedBy = "task")
	private Set<Work> works = new HashSet<>();

	@ManyToMany(mappedBy = "tasks")
	private Set<Employee> employees = new HashSet<>();

	public Task() {
	}

	public Task(UUID id,
			UUID creator,
			LocalDateTime createdAt,
			LocalDateTime updatedAt,
			LocalDateTime deletedAt,
			@Valid @NotBlank(message = "Nome é obrigatório") String name,
			@Valid @NotBlank(message = "Descrição é obrigatória") String description,
			TaskStatus status,
			@Valid @NotNull(message = "Projeto é obrigatório") Project project,
			@Valid @Future(message = "Prazo de entrega deve estar no futuro") LocalDateTime deadLine) {

		super(id, creator, createdAt, updatedAt, deletedAt);
		this.name = name;
		this.description = description;
		this.status = status;
		this.project = project;
		this.deadLine = deadLine;
	}

	public String getName() {
		return name;
	}

	public void setName(
			@Valid @NotBlank(message = "Nome é obrigatório") String name) {

		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(
			@Valid @NotBlank(message = "Descrição é obrigatória") String description) {

		this.description = description;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(
			@Valid @NotNull(message = "Projeto é obrigatório") Project project) {

		this.project = project;
	}

	public LocalDateTime getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(
			@Valid @Future(message = "Prazo de entrega deve estar no futuro") LocalDateTime deadLine) {

		this.deadLine = deadLine;
	}

	public Set<Employee> getAllEmployees() {
		return employees;
	}

	public void addEmployee(Employee employee) {
		employees.add(employee);
	}

	public void removeEmployee(Employee employee) {
		employees.remove(employee);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((project == null) ? 0 : project.hashCode());
		result = prime * result + ((deadLine == null) ? 0 : deadLine.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (status != other.status)
			return false;
		if (project == null) {
			if (other.project != null)
				return false;
		} else if (!project.equals(other.project))
			return false;
		if (deadLine == null) {
			if (other.deadLine != null)
				return false;
		} else if (!deadLine.equals(other.deadLine))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Task [id=" + id +
				", name=" + name +
				", creator=" + creator +
				", description=" + description +
				", createdAt=" + createdAt +
				", status=" + status +
				", updatedAt=" + updatedAt +
				", deletedAt=" + deletedAt +
				", project=" + project +
				", deadLine=" + deadLine + "]";
	}
}
