package com.web2.safia.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Work implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@NotBlank(message = "Descrição é obrigatória")
	@Column(name = "description", nullable = false)
	private String description;

	@NotNull(message = "Empregado é obrigatório")
	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;

	@NotNull(message = "Tarefa é obrigatória")
	@ManyToOne
	@JoinColumn(name = "task_id")
	private Task task;

	@NotNull(message = "Inicio é obrigatório")
	@Column(name = "started_at", nullable = false)
	private LocalDateTime startedAt;

	@NotNull(message = "Fim é obrigatório")
	@Column(name = "ended_at", nullable = false)
	private LocalDateTime endedAt;

	public Work() {
	}

	public Work(
			UUID id,
			@Valid @NotBlank(message = "Descrição é obrigatória") String description,
			@Valid @NotNull(message = "Empregado é obrigatório") Employee employee,
			@Valid @NotNull(message = "Tarefa é obrigatória") Task task,
			@Valid @NotNull(message = "Inicio é obrigatório") LocalDateTime startedAt,
			@Valid @NotNull(message = "Fim é obrigatório") LocalDateTime endedAt) {

		this.id = id;
		this.description = description;
		this.employee = employee;
		this.task = task;
		this.startedAt = startedAt;
		this.endedAt = endedAt;
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
			@Valid @NotBlank(message = "Descrição é obrigatória") String description) {

		this.description = description;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(
			@Valid @NotNull(message = "Empregado é obrigatório") Employee employee) {

		this.employee = employee;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(
			@Valid @NotNull(message = "Tarefa é obrigatória") Task task) {

		this.task = task;
	}

	public LocalDateTime getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(
			@Valid @NotNull(message = "Inicio é obrigatório") LocalDateTime startedAt) {

		this.startedAt = startedAt;
	}

	public LocalDateTime getEndedAt() {
		return endedAt;
	}

	public void setEndedAt(
			@Valid @NotNull(message = "Fim é obrigatório") LocalDateTime endedAt) {

		this.endedAt = endedAt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((employee == null) ? 0 : employee.hashCode());
		result = prime * result + ((task == null) ? 0 : task.hashCode());
		result = prime * result + ((startedAt == null) ? 0 : startedAt.hashCode());
		result = prime * result + ((endedAt == null) ? 0 : endedAt.hashCode());
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
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (employee == null) {
			if (other.employee != null)
				return false;
		} else if (!employee.equals(other.employee))
			return false;
		if (task == null) {
			if (other.task != null)
				return false;
		} else if (!task.equals(other.task))
			return false;
		if (startedAt == null) {
			if (other.startedAt != null)
				return false;
		} else if (!startedAt.equals(other.startedAt))
			return false;
		if (endedAt == null) {
			if (other.endedAt != null)
				return false;
		} else if (!endedAt.equals(other.endedAt))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Work [id=" + id + ", description=" + description + ", employee=" + employee + ", task=" + task
				+ ", startedAt=" + startedAt + ", endedAt=" + endedAt + "]";
	}
}
