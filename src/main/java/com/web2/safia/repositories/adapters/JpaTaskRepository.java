package com.web2.safia.repositories.adapters;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;

import com.web2.safia.models.Task;

public interface JpaTaskRepository extends JpaRepository<Task, UUID> {
	@NativeQuery(
		"SELECT t.* FROM task t " +
		"INNER JOIN project p ON t.project_id = p.id " +
		"INNER JOIN employee_to_project etp ON p.id = etp.project_id " +
		"INNER JOIN employee e ON e.id = etp.employee_id " +
		"WHERE t.deleted_at IS NULL AND e.id = :employeeId;")
	Set<Task> findByUserId(@Param("employeeId") UUID employeeId);
}
