package com.web2.safia.models.builders;

import java.time.LocalDateTime;
import java.util.UUID;

import com.web2.safia.models.BaseModel;
import com.web2.safia.models.Employee;

public abstract class BaseBuilder<T extends BaseModel> {
	protected T instance;

	protected BaseBuilder() {
	}

	public BaseBuilder<T> withId(UUID id) {
		instance.setId(id);
		return this;
	}

	public BaseBuilder<T> withCreator(Employee creator) {
		if (creator == null) {
			throw new IllegalArgumentException("Criador não pode ser nulo");
		}

		instance.setCreator(creator);
		return this;
	}

	public BaseBuilder<T> withCreatingAt(LocalDateTime creationDateTime) {
		if (creationDateTime == null) {
			throw new IllegalArgumentException("Data de criação não pode ser nula");
		}

		if (creationDateTime.isAfter(LocalDateTime.now())) {
			throw new IllegalArgumentException("Data de criação não pode estar no futuro");
		}

		instance.setCreatedAt(creationDateTime);
		return this;
	}

	public BaseBuilder<T> withUpdatingAt(LocalDateTime updatingDateTime) {
		if (updatingDateTime == null) {
			throw new IllegalArgumentException("Data de atualização não pode ser nula");
		}

		if (updatingDateTime.isAfter(LocalDateTime.now())) {
			throw new IllegalArgumentException("Data de atualização não pode estar no futuro");
		}

		instance.setUpdatedAt(updatingDateTime);
		return this;
	}

	public BaseBuilder<T> withDeletingAt(LocalDateTime deletingDateTime) {
		if (deletingDateTime == null) {
			throw new IllegalArgumentException("Data de deleção não pode ser nula");
		}

		if (deletingDateTime.isAfter(LocalDateTime.now())) {
			throw new IllegalArgumentException("Data de deleção não pode estar no futuro");
		}

		instance.setDeletedAt(deletingDateTime);
		return this;
	}

	public abstract BaseBuilder<T> builder();

	protected void reset() {
		instance = null;
	}

	public abstract T build();
}
