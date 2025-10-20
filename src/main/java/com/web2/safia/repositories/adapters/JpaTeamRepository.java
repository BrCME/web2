package com.web2.safia.repositories.adapters;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web2.safia.models.Team;

public interface JpaTeamRepository extends JpaRepository<Team, UUID> {
}
