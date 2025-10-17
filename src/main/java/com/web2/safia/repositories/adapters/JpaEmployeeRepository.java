package com.web2.safia.repositories.adapters;

import java.util.UUID;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;

import com.web2.safia.models.Employee;

public interface JpaEmployeeRepository extends JpaRepository<Employee, UUID> {
	@NativeQuery(value = "SELECT e FROM employee e WHERE e.email LIKE :email AND e.deleted_at IS NULL")
	Optional<Employee> findByEmail(@Param("email") String email);
}
