package com.web2.safia.safia.models.builders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.util.StringUtils;

import com.web2.safia.safia.models.Customer;

public class CustomerBuilder extends Builder<Customer> {
	public CustomerBuilder withId(UUID id) {
		if (id == null) {
			throw new IllegalArgumentException("Id não pode ser nulo");
		}

		instance.setId(id);
		return this;
	}

	public CustomerBuilder withName(String name) {
		if (!StringUtils.hasText(name)) {
			throw new IllegalArgumentException("Nome não pode ser nulo");
		}

		instance.setName(name);
		return this;
	}

	public CustomerBuilder withEmail(String email) {

		instance.setEmail(email);
		return this;

	}

	public CustomerBuilder withPassword(String password) {

		instance.setPassword(password);
		return this;

	}

	public CustomerBuilder withBirthDate(LocalDate birthDate) {
		instance.setBirthDate(birthDate);
		return this;
	}

	public CustomerBuilder withCreator(Customer creator) {
		if (creator == null) {
			throw new IllegalArgumentException("Criador não pode ser nulo");
		}

		instance.setCreator(creator);
		return this;
	}

	public CustomerBuilder withCreatingAt(LocalDateTime creationDateTime) {
		if (creationDateTime == null) {
			throw new IllegalArgumentException("Data de criação não pode ser nula");
		}

		if (creationDateTime.isAfter(LocalDateTime.now())) {
			throw new IllegalArgumentException("Data de criação não pode estar no futuro");
		}

		instance.setCreatedAt(creationDateTime);
		return this;
	}

	public CustomerBuilder withUpdatingAt(LocalDateTime updatingDateTime) {
		if (updatingDateTime == null) {
			throw new IllegalArgumentException("Data de atualização não pode ser nula");
		}

		if (updatingDateTime.isAfter(LocalDateTime.now())) {
			throw new IllegalArgumentException("Data de atualização não pode estar no futuro");
		}

		instance.setUpdatedAt(updatingDateTime);
		return this;
	}

	public CustomerBuilder withDeletingAt(LocalDateTime deletingDateTime) {
		if (deletingDateTime == null) {
			throw new IllegalArgumentException("Data de deleção não pode ser nula");
		}

		if (deletingDateTime.isAfter(LocalDateTime.now())) {
			throw new IllegalArgumentException("Data de deleção não pode estar no futuro");
		}

		instance.setDeletedAt(deletingDateTime);
		return this;
	}

	@Override
	public CustomerBuilder builder() {
		instance = new Customer();
		return this;
	}

	@Override
	public Customer build() {
		Customer c = instance;
		reset();
		return c;
	}
}
