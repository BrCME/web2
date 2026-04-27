package com.web2.safia.shared.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.web2.safia.work.api.dto.CreateWorkRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "work")
public class Work implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(name = "description", nullable = false)
	private String description;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;

	@ManyToOne
	@JoinColumn(name = "task_id")
	private Task task;

	@Column(name = "started_at", nullable = false)
	private LocalDateTime startedAt;

	@Column(name = "ended_at", nullable = false)
	private LocalDateTime endedAt;

	public Work() {
		this.startedAt = LocalDateTime.now();
	}

	public Work(UUID id) {
		setId(id);
	}

	public Work(CreateWorkRequestDto requestDto) {
		this.description = requestDto.description();
		this.startedAt = LocalDateTime.now();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(
			@Valid @NotBlank(message = "Description is required") String description) {

		this.description = description;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(
			@Valid @NotNull(message = "Employee is required") Employee employee) {

		this.employee = employee;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(
			@Valid @NotNull(message = "Task is required") Task task) {

		this.task = task;
	}

	public LocalDateTime getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(
			@Valid @NotNull(message = "Started At is required") LocalDateTime startedAt) {

		this.startedAt = startedAt;
	}

	public LocalDateTime getEndedAt() {
		return endedAt;
	}

	public void setEndedAt(
			@Valid @NotNull(message = "Ended At is required") LocalDateTime endedAt) {

		this.endedAt = endedAt;
	}

	public boolean isFinished() {
		return this.endedAt != null;
	}

	public void finish() {
		this.endedAt = LocalDateTime.now();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Work other = (Work) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Work [id=" + id +
				", description=" + description +
				", employee=" + employee +
				", task=" + task.getName() +
				", startedAt=" + startedAt +
				", endedAt=" + endedAt + "]";
	}
}
