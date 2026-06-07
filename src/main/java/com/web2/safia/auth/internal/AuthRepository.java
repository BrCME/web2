package com.web2.safia.auth.internal;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;

import com.web2.safia.shared.vo.UserId;

public interface AuthRepository extends JpaRepository<User, UserId> {
	@NativeQuery("""
			SELECT u.*, (u.deleted_at IS NULL) AS non_expired, (u.status != 'BLOCKED') AS non_locked, (u.status != 'BLOCKED' AND u.deleted_at IS NULL) AS enabled
			FROM auth."user" u
			WHERE u.username LIKE :username
			""")
	public Optional<User> findUserByUsername(@Param("username") String username);

	@NativeQuery("""
			SELECT r.id, r.type
			FROM auth."role" r
			WHERE r.type::VARCHAR(127) IN :names
			""")
	public Set<Role> findAllRolesByName(@Param("names") Set<String> names);

	@NativeQuery("""
			SELECT r.id, r.type
			FROM auth."role" r
			""")
	public Set<Role> findAllRoles();
}
