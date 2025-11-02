package com.web2.safia.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web2.safia.models.Team;
import com.web2.safia.repositories.adapters.JpaTeamRepository;

@Service
public class TeamService {
	private static final Logger logger = LoggerFactory.getLogger(TeamService.class);

	private final JpaTeamRepository teamRepository;

	public TeamService(JpaTeamRepository teamRepository) {
		this.teamRepository = teamRepository;
	}

	public Page<Team> getAll(Pageable pageable) {
		logger.info("Buscando todos os times com paginação: {}", pageable);
		return teamRepository.findAll(pageable);
	}
}
