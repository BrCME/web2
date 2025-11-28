package com.web2.safia.repositories.adapters;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;

import com.web2.safia.models.Project;

public interface JpaProjectRepository extends JpaRepository<Project, UUID> {
	@NativeQuery(
		"SELECT p.* FROM project p " +
		"INNER JOIN employee_to_project etp ON p.id = etp.project_id " +
		"INNER JOIN employee e ON e.id = etp.employee_id " +
		"WHERE p.deleted_at IS NULL AND e.id = :employeeId;")
	Set<Project> findByUserId(@Param("employeeId") UUID employeeId);
}
