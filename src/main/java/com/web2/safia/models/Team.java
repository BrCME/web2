package com.web2.safia.models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Team extends Base {
	@NotBlank(message = "Nome é obrigatório")
	@Column(name = "name", nullable = false)
	private String name;

	@NotBlank(message = "Descrição é obrigatória")
	@Column(name = "description", nullable = false)
	private String description;

	@ManyToMany
	@JoinTable(name = "employee_to_team", joinColumns = @JoinColumn(name = "team_id"), inverseJoinColumns = @JoinColumn(name = "employee_id"))
	@ElementCollection
	private Set<Employee> employees = new HashSet<>();

	// @ManyToMany
	// @JoinTable(name = "team_project", joinColumns = @JoinColumn(name =
	// "team_id"), inverseJoinColumns = @JoinColumn(name = "project_id"))
	// @ElementCollection
	// private Set<Project> projects = new HashSet<>();

	public Team() {
	}

	public Team(
			UUID id,
			UUID creator,
			LocalDateTime createdAt,
			LocalDateTime updatedAt,
			LocalDateTime deletedAt,
			@Valid @NotBlank(message = "Nome é obrigatório") String name,
			@Valid @NotBlank(message = "Descrição é obrigatória") String description) {

		super(id, creator, createdAt, updatedAt, deletedAt);
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(@Valid @NotBlank(message = "Nome é obrigatório") String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(@Valid @NotBlank(message = "Descrição é obrigatória") String description) {
		this.description = description;
	}

	public Set<Employee> getAllEmployees() {
		return Set.copyOf(employees);
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
		result = prime * result + ((description == null) ? 0 : description.hashCode());
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
		Team other = (Team) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Team [id=" + id +
				", name=" + name +
				", creator=" + creator +
				", description=" + description +
				", createdAt=" + createdAt +
				", updatedAt=" + updatedAt +
				", deletedAt=" + deletedAt +
				", employees=" + employees + "]";
	}
}
