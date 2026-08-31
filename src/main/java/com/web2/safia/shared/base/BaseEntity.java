package com.web2.safia.shared.base;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.web2.safia.shared.entity.Employee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseEntity implements Serializable {
	protected static final long serialVersionUID = 1L;

	@Id
	protected UUID id;

	@CreatedBy
	@ManyToOne
	@JoinColumn(name = "created_by")
	protected Employee creator;

	@CreatedDate
	@Column(name = "created_at", nullable = false)
	protected LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "updated_at", updatable = true)
	protected LocalDateTime updatedAt;

	@Column(name = "deleted_at")
	protected LocalDateTime deletedAt;

	protected BaseEntity() {
		this.id = UUID.randomUUID();
		this.createdAt = LocalDateTime.now(ZoneOffset.UTC);
	}

	protected BaseEntity(UUID id) {
		setId(id);
	}

	public boolean isEnabled() {
		return deletedAt != null;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Employee getCreator() {
		return creator;
	}

	public void setCreator(Employee creator) {
		this.creator = creator;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public LocalDateTime getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(LocalDateTime deletedAt) {
		this.deletedAt = deletedAt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		BaseEntity other = (BaseEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
