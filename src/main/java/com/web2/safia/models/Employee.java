package com.web2.safia.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Entity
public class Employee extends BaseModel {
	@Column(name = "name", nullable = false)
	@NotBlank(message = "Nome é obrigatório")
	private String name;

	@Column(name = "email", nullable = false, unique = true)
	@Email(message = "Email com formato inválido")
	private String email;

	@Column(name = "password", nullable = false)
	@Size(max = 20, min = 8, message = "Senha deve conter entre 8 a 20 letras")
	private String password;

	@Column(name = "phone_number", nullable = false, unique = true)
	@Size(max = 11, min = 11, message = "Tamanho de telefone precisa ser 11")
	private String phoneNumber;

	@Column(name = "cpf", nullable = false, unique = true)
	@Size(max = 11, min = 11, message = "Tamanho de CPF precisa ser 11")
	private String cpf;

	@Column(name = "birth_date", nullable = false)
	@Past(message = "Data de nascimento deve estar no passado")
	private LocalDate birthDate;

	@ManyToMany(mappedBy = "employees")
	private Set<Team> teams = new HashSet<>();

	@ManyToMany(mappedBy = "employees")
	private Set<Project> projects = new HashSet<>();

	@OneToMany(mappedBy = "employee")
	private Set<Work> works = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "work", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "task_id"))
	private Set<Task> tasks = new HashSet<>();

	public Employee() {
		super();
	}

	public Employee(
			UUID id,
			Employee creator,
			LocalDateTime createdAt,
			LocalDateTime updatedAt,
			LocalDateTime deletedAt,
			@NotBlank(message = "Nome é obrigatório") String name,
			@Email(message = "Email com formato inválido") String email,
			@Size(max = 20, min = 8, message = "Senha deve conter entre 8 a 20 letras") String password,
			@Size(max = 11, min = 11, message = "Tamanho de telefone precisa ser 11") String phoneNumber,
			@Size(max = 11, min = 11, message = "Tamanho de CPF precisa ser 11") String cpf,
			@Past(message = "Data de nascimento deve estar no passado") LocalDate birthDate) {

		super(id, creator, createdAt, updatedAt, deletedAt);
		this.name = name;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.cpf = cpf;
		this.birthDate = birthDate;
	}

	public String getName() {
		return name;
	}

	public void setName(
			@Valid @NotBlank(message = "Nome é obrigatório") String name) {

		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(
			@Valid @Email(message = "Email com formato inválido") String email) {

		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(
			@Valid @Size(max = 20, min = 8, message = "Senha deve conter entre 8 a 20 letras") String password) {

		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(
			@Size(max = 11, min = 11, message = "Tamanho de telefone precisa ser 11") String phoneNumber) {

		this.phoneNumber = phoneNumber;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(
			@Valid @Size(max = 11, min = 11, message = "Tamanho de CPF precisa ser 11") String cpf) {

		this.cpf = cpf;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(
			@Valid @Past(message = "Data de nascimento deve estar no passado") LocalDate birthDate) {

		this.birthDate = birthDate;
	}

	public Set<Team> getAllTeams() {
		return Set.copyOf(teams);
	}

	public void addTeam(Team team) {
		teams.add(team);
	}

	public void removeTeam(Team team) {
		teams.remove(team);
	}

	public Set<Project> getAllProjects() {
		return Set.copyOf(projects);
	}

	public void addProject(Project project) {
		projects.add(project);
	}

	public void removeProject(Project project) {
		projects.remove(project);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
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
		Employee other = (Employee) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Employee [name=" + name +
				", email=" + email +
				", password=" + password +
				", phoneNumber=" + phoneNumber +
				", cpf=" + cpf +
				", birthDate=" + birthDate + "]";
	}
}
