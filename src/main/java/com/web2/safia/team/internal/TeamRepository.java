package com.web2.safia.team.internal;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;

import com.web2.safia.shared.entity.Team;

public interface TeamRepository extends JpaRepository<Team, UUID> {
	@NativeQuery("SELECT t.* " +
			"FROM team t " +
			"INNER JOIN employee e ON t.creator_id = e.id " +
			"WHERE e.id = :issuerId " +
			"GROUP BY t.created_at 'ASC'")
	Page<Team> findAllByIssuerId(Pageable pageable, UUID issuerId);

	@NativeQuery("SELECT t FROM team t " +
			"INNER JOIN employee_to_team ett ON t.id = ett.team_id " +
			"INNER JOIN employee e ON e.id = ett.employee_id " +
			"WHERE t.deleted_at IS NULL AND e.id = :employeeId;")
	Set<Team> findByUserId(@Param("employeeId") UUID employeeId);
}
