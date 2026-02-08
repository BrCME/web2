package com.web2.safia.project;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;

import com.web2.safia.employee.Employee;

public interface JpaProjectRepository extends JpaRepository<Project, UUID> {
	Page<Project> findAllByCreator(Pageable pageable, Employee creator);

	@NativeQuery(
		"SELECT p FROM project p " +
		"INNER JOIN employee_to_project etp ON p.id = etp.project_id " +
		"INNER JOIN employee e ON e.id = etp.employee_id " +
		"WHERE p.deleted_at IS NULL AND e.id = :employeeId;")
	Set<Project> findByUserId(@Param("employeeId") UUID employeeId);
}
