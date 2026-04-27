package com.web2.safia.task.internal;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;

import com.web2.safia.shared.entity.Task;

public interface TaskRepository extends JpaRepository<Task, UUID> {
	@NativeQuery("SELECT t.* " +
		"FROM task t " +
		"INNER JOIN employee e ON t.creator_id = e.id " +
		"WHERE e.id = :issuerId " +
		"GROUP BY t.created_at 'ASC'"
	)
	Page<Task> findAllByIssuerId(Pageable pageable, UUID issuerId);

	@NativeQuery(
		"SELECT t FROM task t " +
		"INNER JOIN project p ON t.project_id = p.id " +
		"INNER JOIN employee_to_project etp ON p.id = etp.project_id " +
		"INNER JOIN employee e ON e.id = etp.employee_id " +
		"WHERE t.deleted_at IS NULL AND e.id = :employeeId;")
	Set<Task> findByUserId(@Param("employeeId") UUID employeeId);
}
