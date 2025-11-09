package com.web2.safia.repositories.adapters;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

import com.web2.safia.models.Team;

import io.lettuce.core.dynamic.annotation.Param;

public interface JpaTeamRepository extends JpaRepository<Team, UUID> {
	@NativeQuery("SELECT t.id, t.name, t.description, t.created_by, t.created_at, t.updated_at, t.deleted_at FROM team t WHERE t.created_by = :creatorId AND t.deleted_at IS NULL")
	Page<Team> findAllByCreator(Pageable pageable, @Param("creatorId") UUID creatorId);
}
