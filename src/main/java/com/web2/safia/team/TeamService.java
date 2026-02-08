package com.web2.safia.team;

import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.web2.safia.commit.Commit;
import com.web2.safia.commit.events.CreateCommitEvent;
import com.web2.safia.common.BaseService;
import com.web2.safia.employee.Employee;
import com.web2.safia.exceptions.DomainException;
import com.web2.safia.exceptions.EntityNotFoundException;
import com.web2.safia.team.dtos.BriefTeamResponseDto;
import com.web2.safia.team.events.TeamByIdRequestEvent;
import com.web2.safia.team.events.GetTeamByIdRequestEvent;
import com.web2.safia.team.events.GetTeamByIdResponseEvent;

import jakarta.validation.Valid;

public class TeamService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(TeamService.class);

	private final JpaTeamRepository teamRepository;

	public TeamService(
			ApplicationEventPublisher eventPublisher,
			JpaTeamRepository teamRepository) {

		super(eventPublisher);
		this.teamRepository = teamRepository;
	}

	public Page<Team> getAll(Pageable pageable) {
		return teamRepository.findAll(pageable);
	}

	public Page<Team> getAllByCreator(Pageable pageable, Employee creator) {
		return teamRepository.findAllByCreator(pageable, creator);
	}

	public Team getById(UUID id) throws DomainException {
		var team = teamRepository.findById(id);

		if (!team.isPresent()) {
			logger.error("Equipe com id '{}' não encontrada", id);
			throw new DomainException(String.format("Equipe com id '%s' não encontrada", id));
		}

		return team.get();
	}

	public void create(@Valid Team team, Employee creator) {
		team.setCreator(creator);
		team.addEmployee(creator);
		teamRepository.save(team);
		logger.info("Criada equipe '{}' novo por '{}", team.getName(), creator.getEmail());

		eventPublisher.publishCreateCommitEvent(
				String.format("Criada equipe '%s' novo", team.getName()),
				creator);
	}

	public void deleteById(UUID id, Employee creator) throws DomainException {
		var team = teamRepository.findById(id);

		if (!team.isPresent()) {
			logger.error("Equipe '{}' não encontrada para deletar", id);
			throw new DomainException();
		}

		team.get().setDeletedAt(LocalDateTime.now());
		teamRepository.save(team.get());

		eventPublisher.publishDeactivateCommitEvent(
				String.format("Deletada equipe '%s'", team.get().getName()),
				creator);
	}

	public void updateById(@Valid Team team, Employee creator) throws DomainException {
		var actualTeam = teamRepository.findById(team.getId());

		if (!actualTeam.isPresent()) {
			logger.error("Equipe '{}' não encontrado para atualizar", team.getId());
			throw new DomainException("Equipe não encontrada");
		}

		actualTeam.get().setName(team.getName());
		actualTeam.get().setDescription(team.getDescription());

		teamRepository.save(actualTeam.get());
		eventPublisher.publishUpdateCommitEvent(
				String.format("Atualizada equipe '%s'", actualTeam.get().getName()),
				creator);
	}

	public void addEmployee(@Valid Team team, String employeeEmail, Employee creator) throws DomainException {
		var actualTeam = teamRepository.findById(team.getId());

		if (!actualTeam.isPresent()) {
			logger.error("Equipe '{}' não encontrada para adicionar empregado '{}'", team.getId(), employeeEmail);
			throw new DomainException("Equipe não encontrada para adicionar empregado");
		}

		var employee = employeeRepository.findByEmail(employeeEmail);

		if (!employee.isPresent()) {
			logger.error("Empregado '{}' não encontrado para adicionar à equipe '{}'", employeeEmail, team.getName());
			throw new DomainException("Empregado não encontrado para adicionar à equipe");
		}

		if (!actualTeam.get().addEmployee(employee.get())) {
			logger.error("Não foi possível adicionar o empregado '{}' à equipe '{}'", employee.get().getEmail(),
					actualTeam.get().getName());
			throw new DomainException("Não foi possível adicionar empregado à equipe");
		}

		teamRepository.save(actualTeam.get());
		eventPublisher.publishUpdateCommitEvent(
				String.format("Atualizada equipe '%s' com novo empregado '%s'", actualTeam.get().getName(),
						employee.get().getEmail()),
				creator);
	}

	public void removeEmployee(@Valid Team team, UUID employeeId, Employee user) throws DomainException {
		var actualTeam = teamRepository.findById(team.getId());

		if (!actualTeam.isPresent()) {
			logger.error("Equipe '{}' não encontrado para remover empregado '{}'", team.getId(),
					employeeId);
			throw new DomainException("Equipe não encontrado para remover empregado");
		}

		var employee = employeeRepository.findById(employeeId);

		if (!employee.isPresent()) {
			logger.error("Empregado '{}' não encontrado para remover da equipe '{}'", employeeId, team.getName());
			throw new DomainException("Empregado não encontrado para remover da equipe");
		}

		if (!actualTeam.get().removeEmployee(employee.get())) {
			logger.error("Não foi possivel remover o empregado '{}' da equipe '{}'", employee.get().getEmail(),
					actualTeam.get().getName());
			throw new DomainException("Não foi possível remover empregado da equipe");
		}

		teamRepository.save(actualTeam.get());
		eventPublisher.publishEvent(
				new CreateCommitEvent(
						String.format("Atualizada equipe '%s'", actualTeam.get().getName()),
						Commit.Type.UPDATE,
						user));
	}

	@EventListener
	public void onGetTeamByIdRequestEvent(GetTeamByIdRequestEvent requestEvent) throws EntityNotFoundException {
		var team = teamRepository
				.findById(requestEvent.id())
				.orElseThrow(() -> {
					logger.error("Team with id '{}' not found", requestEvent.id());
					throw new EntityNotFoundException(
							String.format("Team with id '%s' not found", requestEvent.id().toString()));
				});

		eventPublisher.publishEvent(new GetTeamByIdResponseEvent(team));
	}
}
