package com.web2.safia.shared.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;

import com.web2.safia.shared.base.BaseEntity;
import com.web2.safia.task.api.dto.CreateTaskRequest;
import com.web2.safia.task.internal.TaskStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "task")
public class Task extends BaseEntity {
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description", nullable = false)
	private String description;

	@JdbcType(value = PostgreSQLEnumJdbcType.class)
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private TaskStatus status;

	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;

	@Column(name = "dead_line", nullable = false)
	private LocalDateTime deadLine;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "task")
	private final Set<Work> works = new HashSet<>();

	public Task() {
		super();
	}

	public Task(UUID id) {
		super(id);
	}

	public Task(CreateTaskRequest request) {
		super();
		this.name = request.name();
		this.description = request.description();
		this.deadLine = request.deadLine();
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

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(@Valid @NotNull(message = "Project cannot be null") Project project) {
		this.project = project;
	}

	public LocalDateTime getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(@Valid @Future(message = "Deadline must be in future") LocalDateTime deadLine) {
		this.deadLine = deadLine;
	}

	public Set<Employee> getAllEmployees() {
		return works
				.stream()
				.map(Work::getEmployee)
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
		if (this.status.equals(TaskStatus.DONE)) {
			return false;
		}

		this.status = switch (this.status) {
			case TaskStatus.TO_DO -> TaskStatus.DOING;
			case TaskStatus.DOING -> TaskStatus.IN_ANALYSYS;
			case TaskStatus.IN_ANALYSYS -> TaskStatus.DONE;
			default -> this.status;
		};

		return true;
	}

	public boolean demote() {
		if (this.status.equals(TaskStatus.TO_DO)) {
			return false;
		}

		this.status = switch (this.status) {
			case TaskStatus.DONE -> TaskStatus.IN_ANALYSYS;
			case TaskStatus.IN_ANALYSYS -> TaskStatus.DOING;
			case TaskStatus.DOING -> TaskStatus.TO_DO;
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
				", status=" + status.name() +
				", updatedAt=" + updatedAt +
				", deletedAt=" + deletedAt +
				", project=" + project +
				", deadLine=" + deadLine + "]";
	}
}
