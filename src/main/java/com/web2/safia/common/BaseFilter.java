package com.web2.safia.common;

import java.time.LocalDateTime;
import java.util.UUID;

@Deprecated
public abstract class BaseFilter {
	protected UUID id;
	protected UUID creatorId;
	protected String creatorEmail;
	protected LocalDateTime createdAtStartDate;
	protected LocalDateTime createdAtEndDate;
	protected LocalDateTime updatedAtStartDate;
	protected LocalDateTime updatedAtEndDate;
	protected LocalDateTime deletedAtStartDate;
	protected LocalDateTime deletedAtEndDate;
	protected String sortBy;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(UUID creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorEmail() {
		return creatorEmail;
	}

	public void setCreatorEmail(String creatorEmail) {
		this.creatorEmail = creatorEmail;
	}

	public LocalDateTime getCreatedAtStartDate() {
		return createdAtStartDate;
	}

	public void setCreatedAtStartDate(LocalDateTime createdAtStartDate) {
		this.createdAtStartDate = createdAtStartDate;
	}

	public LocalDateTime getCreatedAtEndDate() {
		return createdAtEndDate;
	}

	public void setCreatedAtEndDate(LocalDateTime createdAtEndDate) {
		this.createdAtEndDate = createdAtEndDate;
	}

	public LocalDateTime getUpdatedAtStartDate() {
		return updatedAtStartDate;
	}

	public void setUpdatedAtStartDate(LocalDateTime updatedAtStartDate) {
		this.updatedAtStartDate = updatedAtStartDate;
	}

	public LocalDateTime getUpdatedAtEndDate() {
		return updatedAtEndDate;
	}

	public void setUpdatedAtEndDate(LocalDateTime updatedAtEndDate) {
		this.updatedAtEndDate = updatedAtEndDate;
	}

	public LocalDateTime getDeletedAtStartDate() {
		return deletedAtStartDate;
	}

	public void setDeletedAtStartDate(LocalDateTime deletedAtStartDate) {
		this.deletedAtStartDate = deletedAtStartDate;
	}

	public LocalDateTime getDeletedAtEndDate() {
		return deletedAtEndDate;
	}

	public void setDeletedAtEndDate(LocalDateTime deletedAtEndDate) {
		this.deletedAtEndDate = deletedAtEndDate;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	@Override
	public String toString() {
		return "BaseFilter [id=" + id +
				", creatorId=" + creatorId +
				", creatorEmail=" + creatorEmail +
				", createdAtStartDate=" + createdAtStartDate +
				", createdAtEndDate=" + createdAtEndDate +
				", updatedAtStartDate=" + updatedAtStartDate +
				", updatedAtEndDate=" + updatedAtEndDate +
				", deletedAtStartDate=" + deletedAtStartDate +
				", deletedAtEndDate=" + deletedAtEndDate +
				", sortBy=" + sortBy + "]";
	}
}

// UUID id; => 'id LIKE :id'
// UUID creatorId; => 'created_by = :creadorId'
// LocalDateTime createdAt => 'created_at BETWEEN :startCreatedAtDate AND
// :endCreatedAtDate'
// LocalDateTime updatedAt; => 'updated_at BETWEEN :startUpdatedAtDate AND
// :endUpdatedAtDate'
// LocalDateTime deletedAt; => 'deleted_at BETWEEN :startDeletedAtDate AND
// :endDeletedAtDate'
// LocalDateTime deletedAt; => 'deleted_at IS NOT NULL'
