package com.web2.safia.work;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;

import com.web2.safia.employee.Employee;

public interface JpaWorkRepository extends JpaRepository<Work, UUID> {
	Page<Work> findAllByEmployee(Pageable pageable, Employee employee);

	@NativeQuery(
		"SELECT w FROM work w " +
		"INNER JOIN employee e ON e.id = w.employee_id " +
		"WHERE e.id = :employeeId;")
	Set<Work> findByUserId(@Param("employeeId") UUID employeeId);
}
