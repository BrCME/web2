package com.web2.safia.models.builders;

import java.time.LocalDateTime;
import java.util.UUID;

import com.web2.safia.models.Commit;
import com.web2.safia.models.CommitType;
import com.web2.safia.models.Employee;

public class CommitBuilder extends Builder<Commit> {
	public CommitBuilder withId(UUID id) {
		instance.setId(id);
		return this;
	}

	public CommitBuilder withCreator(Employee creator) {
		if (creator == null) {
			throw new IllegalArgumentException("Criador não pode ser nulo");
		}

		instance.setCreator(creator);
		return this;
	}

	public CommitBuilder withCreatingAt(LocalDateTime creationDateTime) {
		if (creationDateTime == null) {
			throw new IllegalArgumentException("Data de criação não pode ser nula");
		}

		if (creationDateTime.isAfter(LocalDateTime.now())) {
			throw new IllegalArgumentException("Data de criação não pode estar no futuro");
		}

		instance.setCreatedAt(creationDateTime);
		return this;
	}

	public CommitBuilder withUpdatingAt(LocalDateTime updatingDateTime) {
		if (updatingDateTime == null) {
			throw new IllegalArgumentException("Data de atualização não pode ser nula");
		}

		if (updatingDateTime.isAfter(LocalDateTime.now())) {
			throw new IllegalArgumentException("Data de atualização não pode estar no futuro");
		}

		instance.setUpdatedAt(updatingDateTime);
		return this;
	}

	public CommitBuilder withDeletingAt(LocalDateTime deletingDateTime) {
		if (deletingDateTime == null) {
			throw new IllegalArgumentException("Data de deleção não pode ser nula");
		}

		if (deletingDateTime.isAfter(LocalDateTime.now())) {
			throw new IllegalArgumentException("Data de deleção não pode estar no futuro");
		}

		instance.setDeletedAt(deletingDateTime);
		return this;
	}

	public CommitBuilder withDescription(String description) {
		instance.setDescription(description);
		return this;
	}

	public CommitBuilder withType(CommitType type) {
		instance.setType(type);
		return this;
	}

	@Override
	public CommitBuilder builder() {
		instance = new Commit();
		return this;
	}

	@Override
	public Commit build() {
		var builded = instance;
		reset();
		return builded;
	}
}
