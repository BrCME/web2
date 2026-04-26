package com.web2.safia.employee.internal;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
	@Query(value = "SELECT e FROM Employee e WHERE e.email LIKE :email AND e.deletedAt IS NULL AND e.status IN ('PENDING', 'ACTIVE')")
	Optional<Employee> findByEmail(@Param("email") String email);

	@Query(value = "SELECT e FROM Employee e WHERE e.creator.getId() = :creatorId AND e.deletedAt IS NULL AND e.status IN ('PENDING', 'ACTIVE')")
	Set<Employee> findAllByCreator(@Param("creatorId") UUID creatorId);
}
