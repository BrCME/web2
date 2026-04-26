package com.web2.safia.team.internal;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.web2.safia.employee.internal.Employee;
import com.web2.safia.project.internal.Project;
import com.web2.safia.shared.base.BaseEntity;
import com.web2.safia.team.api.dto.CreateTeamRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "team")
public class Team extends BaseEntity {
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description", nullable = false)
	private String description;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "employee_to_team", joinColumns = @JoinColumn(name = "team_id"), inverseJoinColumns = @JoinColumn(name = "employee_id"))
	@ElementCollection
	private final Set<Employee> employees = new HashSet<>();

	@OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
	private final Set<Project> projects = new HashSet<>();

	public Team() {
		super();
	}

	public Team(UUID id) {
		super(id);
	}

	public Team(
			UUID id,
			Employee creator,
			LocalDateTime createdAt,
			LocalDateTime updatedAt,
			LocalDateTime deletedAt,
			String name,
			String description) {

		super(id, creator, createdAt, updatedAt, deletedAt);
		this.name = name;
		this.description = description;
	}

	public Team(CreateTeamRequestDto requestDto) {
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

	public Set<Employee> getAllEmployees() {
		return Set.copyOf(employees);
	}
	
	public boolean addEmployee(Employee employee) {
		return employees.add(employee);
	}

	public boolean removeEmployee(Employee employee) {
		return employees.remove(employee);
	}

	public Set<Project> getAllProjects() {
		return Set.copyOf(projects);
	}

	public boolean addProject(Project project) {
		return projects.add(project);
	}

	public boolean removeProject(Project project) {
		return projects.remove(project);
	}

	@Override
	public String toString() {
		return "Team [id=" + id +
				", name=" + name +
				", creator=" + creator.getName() +
				", description=" + description +
				", createdAt=" + createdAt +
				", updatedAt=" + updatedAt +
				", deletedAt=" + deletedAt +
				", employees=" + employees.size() + "]";
	}
}
