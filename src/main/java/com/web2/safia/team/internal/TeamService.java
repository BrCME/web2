package com.web2.safia.team.internal;

import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web2.safia.commit.api.CommitType;
import com.web2.safia.commit.api.CreateCommitEvent;
import com.web2.safia.employee.internal.Employee;
import com.web2.safia.shared.base.BaseService;
import com.web2.safia.shared.exception.DomainException;
import com.web2.safia.shared.exception.EntityNotFoundException;
import com.web2.safia.team.api.BriefTeamResponseDto;
import com.web2.safia.team.api.CreateTeamRequestDto;
import com.web2.safia.team.api.UpdateTeamRequestDto;

@Service
public class TeamService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(TeamService.class);

	private final JpaTeamRepository teamRepository;

	public TeamService(
			ApplicationEventPublisher eventPublisher,
			JpaTeamRepository teamRepository) {

		super(eventPublisher);
		this.teamRepository = teamRepository;
	}

	public Page<BriefTeamResponseDto> getAll(Pageable pageable) {
		return teamRepository
				.findAll(pageable)
				.map(team -> new BriefTeamResponseDto(team));
	}

	public Page<BriefTeamResponseDto> getAllByIssuer(Pageable pageable, Employee user) {
		return teamRepository
				.findAllByCreator(pageable, user)
				.map(team -> new BriefTeamResponseDto(team));
	}

	public BriefTeamResponseDto getById(UUID id) {
		return teamRepository
				.findById(id)
				.map(team -> new BriefTeamResponseDto(team))
				.orElseThrow(() -> {
					logger.error("Team with id '{}' not found", id);
					throw new EntityNotFoundException(String.format("Team with id '%s' not found", id));
				});
	}

	public BriefTeamResponseDto create(CreateTeamRequestDto requestDto, Employee user) {
		var team = new Team(requestDto);

		requestDto.employeesId()
				.forEach(id -> logger.info("Employee to add: {}", id));

		requestDto.projectsId()
				.forEach(id -> logger.info("Project to add: {}", id));

		team.setCreator(user);
		team.addEmployee(user);

		teamRepository.save(team);
		logger.info("Team created with name '{}' by '{}", team.getName(), user.getEmail());

		eventPublisher.publishEvent(
				new CreateCommitEvent(
						String.format("Team created with name '%s'", team.getName()),
						CommitType.CREATE,
						user));

		return new BriefTeamResponseDto(team);
	}

	public void deleteById(UUID id, Employee user) {
		var team = teamRepository
				.findById(id)
				.orElseThrow(() -> {
					logger.error("Team with id '{}' not found to deactivate", id);
					throw new EntityNotFoundException(
							String.format("Team with id '%s' not found to deactivate", id.toString()));
				});

		team.setDeletedAt(LocalDateTime.now());
		teamRepository.save(team);

		eventPublisher.publishEvent(
				new CreateCommitEvent(
						String.format("Team with id '%s' deactivated", id.toString()),
						CommitType.DEACTIVATE,
						user));
	}

	public BriefTeamResponseDto updateById(UUID id, UpdateTeamRequestDto requestDto, Employee user) {
		var team = teamRepository
				.findById(id)
				.orElseThrow(() -> {
					logger.error("Team with id '{}' not found to update", id);
					throw new EntityNotFoundException(
							String.format("Team with id '%s' not found to update", id.toString()));
				});

		if (!team.isEnabled()) {
			logger.error("Team with id '{}' not able to update", id);
			throw new DomainException(
					String.format("Team with id '%s' not able to update", id.toString()));
		}

		requestDto
				.name()
				.ifPresent(name -> team.setName(name));

		requestDto
				.description()
				.ifPresent(description -> team.setDescription(description));

		teamRepository.save(team);
		eventPublisher.publishEvent(
				new CreateCommitEvent(
						String.format("Team with id '%s' updated", id.toString()),
						CommitType.UPDATE,
						user));

		return new BriefTeamResponseDto(team);
	}

	public BriefTeamResponseDto addEmployee(UUID id, UUID employeeId, Employee user) {
		var team = teamRepository
				.findById(id)
				.orElseThrow(() -> {
					logger.error("Team with id '{}' not found to add employee '{}'", id, employeeId);
					throw new EntityNotFoundException(
							String.format("Team with id '%s' not found to add employee '%d'",
									id.toString(), employeeId.toString()));
				});

		if (!team.isEnabled()) {
			logger.error("Team with id '{}' is not able to add employee", id);
			throw new DomainException(String.format("Team with id '{}' is not able to add employee", id.toString()));
		}

		// var employee = employeeRepository.findByEmail(employeeEmail);

		teamRepository.save(team);
		eventPublisher.publishEvent(
				new CreateCommitEvent(
						String.format("Team with id '%s' updated with new employee with id '%s'",
								id.toString(), employeeId.toString()),
						CommitType.UPDATE,
						user));

		return new BriefTeamResponseDto(team);
	}

	public BriefTeamResponseDto removeEmployee(UUID id, UUID employeeId, Employee user) {
		var team = teamRepository
				.findById(id)
				.orElseThrow(() -> {
					logger.error("Team with id '{}' not found to remove employee '{}'", id, employeeId);
					throw new EntityNotFoundException(
							String.format("Team with id '%s' not found to remove employee '%s'",
									id.toString(), employeeId.toString()));
				});

		if (!team.isEnabled()) {
			logger.error("Team with id '{}' not found to remove employee '{}'", id, employeeId);
			throw new DomainException(
					String.format("Team with id '%s' not found to remove employee '%s'",
							id.toString(), employeeId.toString()));
		}

		teamRepository.save(team);
		eventPublisher.publishEvent(
				new CreateCommitEvent(
						String.format("Team with id '%s' update without employee with id '%s'",
								id.toString(), employeeId.toString()),
						CommitType.UPDATE,
						user));

		return new BriefTeamResponseDto(team);
	}

	// @EventListener
	// public void onGetTeamByIdRequestEvent(GetTeamByIdRequestEvent requestEvent) {
	// 	var team = teamRepository
	// 			.findById(requestEvent.id())
	// 			.orElseThrow(() -> {
	// 				logger.error("Team with id '{}' not found", requestEvent.id());
	// 				throw new EntityNotFoundException(
	// 						String.format("Team with id '%s' not found", requestEvent.id().toString()));
	// 			});

	// 	eventPublisher.publishEvent(new GetTeamByIdResponseEvent(team));
	// }
}
