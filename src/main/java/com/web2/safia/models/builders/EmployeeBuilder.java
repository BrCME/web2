package com.web2.safia.models.builders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.web2.safia.models.Employee;

public class EmployeeBuilder extends Builder<Employee> {
	public EmployeeBuilder withId(UUID id) {
		instance.setId(id);
		return this;
	}

	public EmployeeBuilder withCreator(UUID creator) {
		if (creator == null) {
			throw new IllegalArgumentException("Criador não pode ser nulo");
		}

		instance.setCreator(creator);
		return this;
	}

	public EmployeeBuilder withCreatingAt(LocalDateTime creationDateTime) {
		if (creationDateTime == null) {
			throw new IllegalArgumentException("Data de criação não pode ser nula");
		}

		if (creationDateTime.isAfter(LocalDateTime.now())) {
			throw new IllegalArgumentException("Data de criação não pode estar no futuro");
		}

		instance.setCreatedAt(creationDateTime);
		return this;
	}

	public EmployeeBuilder withUpdatingAt(LocalDateTime updatingDateTime) {
		if (updatingDateTime == null) {
			throw new IllegalArgumentException("Data de atualização não pode ser nula");
		}

		if (updatingDateTime.isAfter(LocalDateTime.now())) {
			throw new IllegalArgumentException("Data de atualização não pode estar no futuro");
		}

		instance.setUpdatedAt(updatingDateTime);
		return this;
	}

	public EmployeeBuilder withDeletingAt(LocalDateTime deletingDateTime) {
		if (deletingDateTime == null) {
			throw new IllegalArgumentException("Data de deleção não pode ser nula");
		}

		if (deletingDateTime.isAfter(LocalDateTime.now())) {
			throw new IllegalArgumentException("Data de deleção não pode estar no futuro");
		}

		instance.setDeletedAt(deletingDateTime);
		return this;
	}

	public EmployeeBuilder withName(String name) {
		instance.setName(name);
		return this;
	}

	public EmployeeBuilder withEmail(String email) {
		instance.setEmail(email);
		return this;

	}

	public EmployeeBuilder withPassword(String password) {
		instance.setPassword(password);
		return this;

	}

	public EmployeeBuilder withBirthDate(LocalDate birthDate) {
		instance.setBirthDate(birthDate);
		return this;
	}

	@Override
	public EmployeeBuilder builder() {
		instance = new Employee();
		return this;
	}

	@Override
	public Employee build() {
		Employee c = instance;
		reset();
		return c;
	}
}
