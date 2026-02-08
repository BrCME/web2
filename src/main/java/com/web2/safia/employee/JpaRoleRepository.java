package com.web2.safia.employee;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;

import com.web2.safia.auth.Role;

public interface JpaRoleRepository extends JpaRepository<Role, UUID> {
	@NativeQuery("SELECT r.id, r.type FROM role r WHERE r.type::VARCHAR IN :roles")
	Set<Role> findAllByTypes(@Param("roles") Set<String> roles);
}
