package com.web2.safia.events;

import java.time.LocalDateTime;

import org.springframework.context.ApplicationEvent;

import com.web2.safia.models.Commit;
import com.web2.safia.models.Employee;

public class CommitEvent extends ApplicationEvent {
	private String description;
	private Commit.Type type;
	private Employee creator;
	private LocalDateTime createdAt;

	public CommitEvent(Object source, String description, Commit.Type type, Employee creator) {
		super(source);
		this.description = description;
		this.type = type;
		this.creator = creator;
		this.createdAt = LocalDateTime.now();
	}

	public String getDescription() {
		return description;
	}

	public Commit.Type getType() {
		return type;
	}

	public Employee getCreator() {
		return creator;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((creator == null) ? 0 : creator.hashCode());
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
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
		CommitEvent other = (CommitEvent) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (type != other.type)
			return false;
		if (creator == null) {
			if (other.creator != null)
				return false;
		} else if (!creator.equals(other.creator))
			return false;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CommitEvent [description=" + description +
				", type=" + type +
				", creator=" + creator +
				", createdAt=" + createdAt + "]";
	}
}
