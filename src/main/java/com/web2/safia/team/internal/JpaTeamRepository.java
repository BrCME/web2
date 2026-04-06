package com.web2.safia.team.internal;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;

import com.web2.safia.employee.internal.Employee;


public interface JpaTeamRepository extends JpaRepository<Team, UUID> {
	Page<Team> findAllByCreator(Pageable pageable, Employee creator);

	@NativeQuery(
		"SELECT t FROM team t " +
		"INNER JOIN employee_to_team ett ON t.id = ett.team_id " +
		"INNER JOIN employee e ON e.id = ett.employee_id " +
		"WHERE t.deleted_at IS NULL AND e.id = :employeeId;")
	Set<Team> findByUserId(@Param("employeeId") UUID employeeId);
}
