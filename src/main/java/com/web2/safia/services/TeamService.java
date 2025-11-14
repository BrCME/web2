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
import com.web2.safia.repositories.adapters.JpaEmployeeRepository;
import com.web2.safia.repositories.adapters.JpaTeamRepository;

import jakarta.validation.Valid;

@Service
public class TeamService {
	private static final Logger logger = LoggerFactory.getLogger(TeamService.class);

	private final CommitEventPublisher commitEventPublisher;
	private final JpaTeamRepository teamRepository;
	private final JpaEmployeeRepository employeeRepository;

	public TeamService(
			CommitEventPublisher commitEventPublisher,
			JpaTeamRepository teamRepository,
			JpaEmployeeRepository employeeRepository) {

		this.commitEventPublisher = commitEventPublisher;
		this.teamRepository = teamRepository;
		this.employeeRepository = employeeRepository;
	}

	public Page<Team> getAll(Pageable pageable) {
		return teamRepository.findAll(pageable);
	}

	public Page<Team> getAllByCreator(Pageable pageable, Employee creator) {
		return teamRepository.findAllByCreator(pageable, creator);
	}

	public void create(@Valid Team team, Employee creator) {
		team.setCreator(creator);
		team.addEmployee(creator);
		teamRepository.save(team);
		logger.info("Criado time '{}' novo por '{}", team.getName(), creator.getEmail());

		commitEventPublisher.publishCreateCommitEvent(
				String.format("Criado time '%s' novo", team.getName()),
				creator);
	}

	public void deleteById(UUID id, Employee creator) throws DomainException {
		var team = teamRepository.findById(id);

		if (!team.isPresent()) {
			logger.error("Time '{}' não encontrado para deletar", id);
			throw new DomainException();
		}

		team.get().setDeletedAt(LocalDateTime.now());
		teamRepository.save(team.get());

		commitEventPublisher.publishDeactivateCommitEvent(
				String.format("Deletado time '%s'", team.get().getName()),
				creator);
	}

	public void updateById(@Valid Team team, Employee creator) throws DomainException {
		var actualTeam = teamRepository.findById(team.getId());

		if (!actualTeam.isPresent()) {
			logger.error("Time '{}' não encontrado para atualizar", team.getId());
			throw new DomainException("Time não encontrado");
		}

		actualTeam.get().setName(team.getName());
		actualTeam.get().setDescription(team.getDescription());

		teamRepository.save(actualTeam.get());
		commitEventPublisher.publishUpdateCommitEvent(
				String.format("Atualizado time '%s'", actualTeam.get().getName()),
				creator);
	}

	public void addEmployee(@Valid Team team, UUID employeeId, Employee creator) throws DomainException {
		var actualTeam = teamRepository.findById(team.getId());
		var employee = employeeRepository.findById(employeeId);

		if (!employee.isPresent()) {
			logger.error("Empregado '{}' não encontrado para adicionar ao time '{}'", employeeId, team.getName());
			throw new DomainException("Empregado não encontrado para adicionar ao Time");
		}

		if (!actualTeam.isPresent()) {
			logger.error("Time '{}' não encontrado para adicionar empregado '{}'", team.getId(),
					employee.get().getEmail());
			throw new DomainException("Time não encontrado para adicionar empregado");
		}

		if (!actualTeam.get().addEmployee(creator)) {
			logger.error("Não foi possível adicionar o empregado '{}' ao time '{}'", employee.get().getEmail(),
					actualTeam.get().getName());
			throw new DomainException("Não foi possível adicionar empregado ao time");
		}

		teamRepository.save(actualTeam.get());
		commitEventPublisher.publishUpdateCommitEvent(
				String.format("Atualizado time '%s' com novo empregado '%s'", actualTeam.get().getName(),
						employee.get().getEmail()),
				creator);
	}

	public void removeEmployee(@Valid Team team, UUID employeeId, Employee creator) throws DomainException {
		var actualTeam = teamRepository.findById(team.getId());
		var employee = employeeRepository.findById(employeeId);

		if (!employee.isPresent()) {
			logger.error("Empregado '{}' não encontrado para remover do time '{}'", employeeId, team.getName());
			throw new DomainException("Empregado não encontrado para remover do Time");
		}

		if (!actualTeam.isPresent()) {
			logger.error("Time '{}' não encontrado para remover empregado '{}'", team.getId(),
					employee.get().getEmail());
			throw new DomainException("Time não encontrado para remover empregado");
		}

		if (!actualTeam.get().removeEmployee(employee.get())) {
			logger.error("Não foi possivel remover o empregado '{}' do time '{}'", employee.get().getEmail(),
					actualTeam.get().getName());
			throw new DomainException("Não foi possível remover empregado do time");
		}

		teamRepository.save(actualTeam.get());
		commitEventPublisher.publishUpdateCommitEvent(
				String.format("Atualizado time '%s'", actualTeam.get().getName()),
				creator);
	}
}
