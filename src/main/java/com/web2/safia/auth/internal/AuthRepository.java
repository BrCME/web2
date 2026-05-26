package com.web2.safia.auth.internal;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;

public interface AuthRepository extends JpaRepository<User, UserId> {
	@NativeQuery("SELECT u.*, (u.deleted_at IS NULL) AS non_expired, (u.status != 'BLOCKED') AS non_locked, (u.status != 'BLOCKED' AND u.deleted_at IS NULL) AS enabled " +
			"FROM user e " +
			"WHERE u.username LIKE :username")
	public Optional<User> findUserByUsername(@Param("username") String username);

	@NativeQuery("SELECT r.id, r.type " +
			"FROM role r " +
			"WHERE r.type IN :role_names")
	public Set<Role> findAllRolesByName(@Param("role_names") Set<String> roleNames);
}
