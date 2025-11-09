package com.web2.safia.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web2.safia.events.CommitEventPublisher;
import com.web2.safia.exceptions.DomainException;
import com.web2.safia.models.Employee;
import com.web2.safia.models.Team;
import com.web2.safia.repositories.adapters.JpaTeamRepository;

import jakarta.validation.Valid;

@Service
public class TeamService {
	private static final Logger logger = LoggerFactory.getLogger(TeamService.class);

	private final CommitEventPublisher commitEventPublisher;
	private final JpaTeamRepository teamRepository;

	public TeamService(
			CommitEventPublisher commitEventPublisher,
			JpaTeamRepository teamRepository) {

		this.commitEventPublisher = commitEventPublisher;
		this.teamRepository = teamRepository;
	}

	public Page<Team> getAll(Pageable pageable) {
		return teamRepository.findAll(pageable);
	}
	
	public Page<Team> getAllByCreator(Pageable pageable, Employee creator) {
		return teamRepository.findAllByCreator(pageable, creator.getId());
	}
	
	public void create(@Valid Team team, Employee creator) {
		teamRepository.save(team);
		logger.info("Criado time '{}' novo por '{}", team.getName(), creator.getEmail());

		commitEventPublisher.publishCreateCommitEvent(
				String.format("Criado time '%s' novo", team.getName()),
				creator);
	}

	public void deleteById(UUID id, Employee creator) throws DomainException {
		var team = teamRepository.findById(id);
		
		if (!team.isPresent()) {
			logger.error("");
			throw new DomainException();
		}
		
		team.get().setDeletedAt(LocalDateTime.now());
		teamRepository.save(team.get());

		commitEventPublisher.publishDeactivateCommitEvent(
			String.format("Deletado time '%s'", team.get().getName()),
			creator);
	}
}
