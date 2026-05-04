package com.web2.safia.auth.internal;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.web2.safia.shared.entity.Employee;
import com.web2.safia.shared.entity.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;

public interface AuthRepository extends JpaRepository<Employee, UUID> {
	@NativeQuery("SELECT e.*, (e.deleted_at IS NULL) AS non_expired, (e.status != 'BLOCKED') AS non_locked, (e.status != 'BLOCKED' AND e.deleted_at IS NULL) AS enabled " +
			"FROM employee e " +
			"WHERE e.email LIKE :username")
	public Optional<Employee> findUserByUsername(@Param("username") String username);

	@NativeQuery("SELECT r.id, r.type " +
			"FROM role r " +
			"WHERE r.type IN :role_names")
	public Set<Role> findAllRolesByName(@Param("role_names") Set<String> roleNames);
}
