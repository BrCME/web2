package com.web2.safia.work.internal;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;

public interface WorkRepository extends JpaRepository<Work, UUID> {
	@NativeQuery("SELECT w.* " +
		"FROM work w " +
		"INNER JOIN employee e ON w.creator_id = e.id " +
		"WHERE e.id = :issuerId " +
		"GROUP BY w.created_at 'ASC'"
	)
	Page<Work> findAllByIssuerId(Pageable pageable, UUID issuerId);

	@NativeQuery(
		"SELECT w FROM work w " +
		"INNER JOIN employee e ON e.id = w.employee_id " +
		"WHERE e.id = :issuerId")
	Set<Work> findByIssuerId(@Param("employeeId") UUID issuerId);
}
