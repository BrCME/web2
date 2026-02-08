package com.web2.safia.task;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;

import com.web2.safia.common.BaseEntity;
import com.web2.safia.employee.Employee;
import com.web2.safia.project.Project;
import com.web2.safia.task.dtos.CreateTaskRequestDto;
import com.web2.safia.work.Work;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Task extends BaseEntity {
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description", nullable = false)
	private String description;

	@JdbcType(value = PostgreSQLEnumJdbcType.class)
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private Status status;

	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;

	@Column(name = "dead_line", nullable = false)
	private LocalDateTime deadLine;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "task")
	private final Set<Work> works = new HashSet<>();

	public Task() {
	}

	public Task(UUID id,
			Employee creator,
			LocalDateTime createdAt,
			LocalDateTime updatedAt,
			LocalDateTime deletedAt,
			String name,
			String description,
			Status status,
			Project project,
			LocalDateTime deadLine) {

		super(id, creator, createdAt, updatedAt, deletedAt);
		this.name = name;
		this.description = description;
		this.status = status;
		this.project = project;
		this.deadLine = deadLine;
	}

	public Task(CreateTaskRequestDto requestDto) {
		super();
		this.name = requestDto.name();
		this.description = requestDto.description();
		this.deadLine = requestDto.deadLine();
	}

	public static enum Status {
		TO_DO, DOING, IN_ANALYSYS, DONE;
	}

	public String getName() {
		return name;
	}

	public void setName(
			@Valid @NotBlank(message = "Name cannot be blank") String name) {

		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(
			@Valid @NotBlank(message = "Description cannot be blank") String description) {

		this.description = description;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(
			@Valid @NotNull(message = "Project cannot be null") Project project) {

		this.project = project;
	}

	public LocalDateTime getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(
			@Valid @Future(message = "Deadline must be in future") LocalDateTime deadLine) {

		this.deadLine = deadLine;
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

	public Set<Employee> getAllEmployees() {
		return works
				.stream()
				.map(work -> work.getEmployee())
				.collect(Collectors.toSet());
	}

	public Set<Work> getAllWorks() {
		return Set.copyOf(works);
	}

	public boolean addWork(Work work) {
		return works.add(work);
	}

	public boolean removeWork(Work work) {
		return works.remove(work);
	}

	public boolean promote() {
		if (this.status.equals(Task.Status.DONE)) {
			return false;
		}

		this.status = switch (this.status) {
			case Task.Status.TO_DO -> Task.Status.DOING;
			case Task.Status.DOING -> Task.Status.IN_ANALYSYS;
			case Task.Status.IN_ANALYSYS -> Task.Status.DONE;
			default -> this.status;
		};

		return true;
	}

	public boolean demote() {
		if (this.status.equals(Task.Status.TO_DO)) {
			return false;
		}

		this.status = switch (this.status) {
			case Task.Status.DONE -> Task.Status.IN_ANALYSYS;
			case Task.Status.IN_ANALYSYS -> Task.Status.DOING;
			case Task.Status.DOING -> Task.Status.TO_DO;
			default -> this.status;
		};

		return true;
	}

	@Override
	public String toString() {
		return "Task [id=" + id +
				", name=" + name +
				", creator=" + creator.getName() +
				", description=" + description +
				", createdAt=" + createdAt +
				", status=" + status +
				", updatedAt=" + updatedAt +
				", deletedAt=" + deletedAt +
				", project=" + project +
				", deadLine=" + deadLine + "]";
	}
}
