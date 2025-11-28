package com.web2.safia.repositories.adapters;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;

import com.web2.safia.models.Work;

public interface JpaWorkRepository extends JpaRepository<Work, UUID> {
	@NativeQuery(
		"SELECT w.* FROM work w " +
		"INNER JOIN employee e ON e.id = w.employee_id " +
		"WHERE e.id = :employeeId;")
	Set<Work> findByUserId(@Param("employeeId") UUID employeeId);
}
