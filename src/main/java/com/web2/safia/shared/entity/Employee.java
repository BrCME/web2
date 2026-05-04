package com.web2.safia.shared.entity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.web2.safia.auth.api.dto.SignUpUserRequest;
import com.web2.safia.shared.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "employee")
public class Employee extends BaseEntity implements UserDetails, CredentialsContainer {
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "phone_number", nullable = false, unique = true)
	private String phoneNumber;

	@Column(name = "cpf", nullable = false, unique = true)
	private String cpf;

	@Column(name = "birth_date", nullable = false)
	private LocalDate birthDate;

	@JdbcType(value = PostgreSQLEnumJdbcType.class)
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private EmployeeStatus status = EmployeeStatus.PENDING;

	@ManyToMany(mappedBy = "employees")
	private Set<Team> teams = new HashSet<>();

	@ManyToMany(mappedBy = "employees")
	private Set<Project> projects = new HashSet<>();

	@OneToMany(mappedBy = "employee")
	private Set<Work> works = new HashSet<>();

	@ManyToMany()
	@JoinTable(name = "work", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "task_id"))
	private Set<Task> tasks = new HashSet<>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "role_to_employee", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public Employee() {
		super();
	}

	public Employee(UUID id) {
		super(id);
	}

	public Employee(SignUpUserRequest request, String encodedPassword) {
		super();
		this.name = request.name();
		this.email = request.email();
		this.password = encodedPassword;
		this.phoneNumber = request.phoneNumber();
		this.cpf = request.cpf();
		this.birthDate = request.birthDate();
		this.status = EmployeeStatus.PENDING;
	}

	public String getName() {
		return name;
	}

	public void setName(@Valid @NotBlank(message = "Name cannot be blank") String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(@Valid @Email(message = "Invalid email") String email) {
		this.email = email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(
			@Valid @Size(max = 20, min = 8, message = "Password must have between 8 and 20 characters") String password) {

		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(
			@Valid @Size(max = 11, min = 11, message = "Phone number must have 11 characters") String phoneNumber) {

		this.phoneNumber = phoneNumber;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(@Valid @Size(max = 11, min = 11, message = "CPF must have 11 characters") String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(@Valid @Past(message = "Birth date must be in past") LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public EmployeeStatus getStatus() {
		return status;
	}

	public void setStatus(EmployeeStatus status) {
		this.status = status;
	}

	public Set<Team> getAllTeams() {
		return Set.copyOf(teams);
	}

	public void joinTeam(Team team) {
		teams.add(team);
	}

	public void exitsTeam(Team team) {
		teams.remove(team);
	}

	public Set<Project> getAllProjects() {
		return Set.copyOf(projects);
	}

	public void joinProject(Project project) {
		projects.add(project);
	}

	public void exitsProject(Project project) {
		projects.remove(project);
	}

	public Set<Work> getAllWorks() {
		return Set.copyOf(works);
	}

	public void addWork(Work work) {
		works.add(work);
	}

	public void removeWork(Work work) {
		works.remove(work);
	}

	public Set<Task> getAllTasks() {
		return Set.copyOf(tasks);
	}

	public void addTask(Task task) {
		tasks.add(task);
	}

	public void removeTask(Task task) {
		tasks.remove(task);
	}

	public Set<Role> getAllRoles() {
		return Set.copyOf(roles);
	}

	public void addRole(Role role) {
		roles.add(role);
	}

	public void removeRole(Role role) {
		roles.remove(role);
	}

	@Override
	public String toString() {
		return "Employee [name=" + name +
				", id=" + id +
				", email=" + email +
				", creator=" + creator +
				", password=" + password +
				", createdAt=" + createdAt +
				", updatedAt=" + updatedAt +
				", phoneNumber=" + phoneNumber +
				", deletedAt=" + deletedAt +
				", cpf=" + cpf +
				", birthDate=" + birthDate +
				", status=" + status.name() +
				// ", teams=" + teams +
				// ", projects=" + projects +
				// ", works=" + works +
				// ", tasks=" + tasks +
				", roles=" + roles + "]";
	}

	@Override
	public void eraseCredentials() {
		password = null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getUsername() {
		return getEmail();
	}
}
