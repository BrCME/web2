package com.web2.safia.models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

	@ManyToOne
	@JoinColumn(name = "team_id")
	private Team team;

	@ManyToOne
	@JoinColumn(name = "manager_id")
	private Employee manager;
	
	@ManyToMany(fetch = FetchType.EAGER)
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
			@Valid @NotNull(message = "Time é obrigatório") Team team,
			Employee manager) {

		super(id, creator, createdAt, updatedAt, deletedAt);
		this.name = name;
		this.description = description;
		this.team = team;
		this.manager = manager;
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

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((team == null) ? 0 : team.hashCode());
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
