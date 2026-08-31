package com.web2.safia.auth.internal;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.web2.safia.shared.vo.UserId;
import com.web2.safia.shared.vo.Username;
import com.web2.safia.shared.vo.converter.UsernameConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Entity(name = "user")
@Table(name = "user", schema = "auth")
public class User implements UserDetails, CredentialsContainer {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UserId id;

	@Column(name = "username", nullable = false, unique = true)
	@Convert(converter = UsernameConverter.class)
	private Username username;

	@Column(name = "password", nullable = false)
	private String password;

	@JdbcType(value = PostgreSQLEnumJdbcType.class)
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private Status status;

	@ManyToMany
	@JoinTable(name = "role_to_user", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"), schema = "auth")
	private Set<Role> roles = new HashSet<>();

	@CreatedDate
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "updated_at", updatable = true)
	private LocalDateTime updatedAt;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	public User() {
		this.id = new UserId();
		this.status = Status.PENDING;
		this.createdAt = LocalDateTime.now(ZoneOffset.UTC);
	}

	public User(UserId id) {
		this.id = id;
		this.status = Status.PENDING;
		this.createdAt = LocalDateTime.now(ZoneOffset.UTC);
	}

	public enum Status {
		PENDING, ACTIVE, BLOCKED;
	}

	public UserId getId() {
		return id;
	}

	public void setId(UserId id) {
		this.id = id;
	}

	@Override
	public String getUsername() {
		return username.value();
	}

	public void setUsername(@Valid @Email(message = "Invalid username") String username) {
		this.username = new Username(username);
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(
			@Valid @Size(max = 20, min = 8, message = "Password must have between 8 and 20 characters") String password) {

		this.password = password;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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
	public void eraseCredentials() {
		password = null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	public void activate() {
		this.status = Status.ACTIVE;
	}

	public boolean isActive() {
		return status.equals(Status.ACTIVE);
	}
}
