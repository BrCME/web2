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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Project extends BaseModel {
	@NotBlank(message = "Nome é obrigatório")
	@Column(name = "name", nullable = false)
	private String name;

	@NotBlank(message = "Descrição é obrigatória")
	@Column(name = "description", nullable = false)
	private String description;

	@NotNull(message = "Time é obrigatório")
	@ManyToOne
	@JoinColumn(name = "team_id")
	private Team team;

	@ManyToMany
	@JoinTable(name = "employee_to_project", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "employee_id"))
	@ElementCollection
	private Set<Employee> employees = new HashSet<>();

	@OneToMany(mappedBy = "project")
	@ElementCollection
	private Set<Task> tasks = new HashSet<>();

	public Project() {
		super();
	}

	public Project(
			UUID id,
			Employee creator,
			LocalDateTime createdAt,
			LocalDateTime updatedAt,
			LocalDateTime deletedAt,
			@Valid @NotBlank(message = "Nome é obrigatório") String name,
			@Valid @NotBlank(message = "Descrição é obrigatória") String description,
			@Valid @NotNull(message = "Time é obrigatório") Team team) {

		super(id, creator, createdAt, updatedAt, deletedAt);
		this.name = name;
		this.description = description;
		this.team = team;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((team == null) ? 0 : team.hashCode());
		return result;
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (team == null) {
			if (other.team != null)
				return false;
		} else if (!team.equals(other.team))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Project [id=" + id +
				", name=" + name +
				", creator=" + creator +
				", createdAt=" + createdAt +
				", description=" + description +
				", updatedAt=" + updatedAt +
				", team=" + team +
				", deletedAt=" + deletedAt +
				", employees=" + employees + "]";
	}
}
