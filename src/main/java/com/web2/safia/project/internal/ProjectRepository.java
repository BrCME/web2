package com.web2.safia.project.internal;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;

import com.web2.safia.shared.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
	@NativeQuery("SELECT p " +
		"FROM project p " +
		"INNER JOIN employee e ON p.creator_id = e.id " +
		"WHERE e.id = :issuerId " +
		"GROUP BY p.created_at 'ASC'"
	)
	Page<Project> findAllByIssuerId(Pageable pageable, UUID issuerId);

	@NativeQuery(
		"SELECT p FROM project p " +
		"INNER JOIN employee_to_project etp ON p.id = etp.project_id " +
		"INNER JOIN employee e ON e.id = etp.employee_id " +
		"WHERE p.deleted_at IS NULL AND e.id = :employeeId;")
	Set<Project> findByUserId(@Param("employeeId") UUID employeeId);
}
