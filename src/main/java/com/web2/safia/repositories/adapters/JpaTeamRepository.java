package com.web2.safia.repositories.adapters;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;

import com.web2.safia.models.Employee;
import com.web2.safia.models.Team;

import java.util.Set;

public interface JpaTeamRepository extends JpaRepository<Team, UUID> {
	Page<Team> findAllByCreator(Pageable pageable, Employee creator);

	@NativeQuery(
		"SELECT t.* FROM team t " +
		"INNER JOIN employee_to_team ett ON t.id = ett.team_id " +
		"INNER JOIN employee e ON e.id = ett.employee_id " +
		"WHERE t.deleted_at IS NULL AND e.id = :employeeId;")
	Set<Team> findByUserId(@Param("employeeId") UUID employeeId);
}
