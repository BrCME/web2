package com.web2.safia.auth.internal;

import java.util.UUID;

import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;
import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "auth.role")
@Table(name = "role", schema = "auth")
public class Role implements GrantedAuthority {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	protected UUID id;

	@JdbcType(value = PostgreSQLEnumJdbcType.class)
	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false)
	private Type type;

	public Role() {
		super();
	}

	public enum Type {
	ADMIN, MANAGER, EMPLOYEE, NEWCOMER;
}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Type getType() {
		return type;
	}

	public void setType(Role.Type type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Role other = (Role) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return (type != other.type);
	}

	@Override
	public String toString() {
		return type.toString();
	}

	@Override
	public String getAuthority() {
		return "ROLE_" + type.toString();
	}
}
