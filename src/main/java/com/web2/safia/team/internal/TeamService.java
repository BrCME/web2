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
import com.web2.safia.commit.api.event.SystemCommitOcurredEvent;
import com.web2.safia.employee.internal.Employee;
import com.web2.safia.project.internal.Project;
import com.web2.safia.shared.base.BaseService;
import com.web2.safia.shared.exception.DomainException;
import com.web2.safia.shared.exception.EntityNotFoundException;
import com.web2.safia.team.api.dto.BriefTeamResponseDto;
import com.web2.safia.team.api.dto.CreateTeamRequestDto;
import com.web2.safia.team.api.dto.UpdateTeamRequestDto;

@Service
public class TeamService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(TeamService.class);

	private final TeamRepository teamRepository;

	public TeamService(
			ApplicationEventPublisher eventPublisher,
			TeamRepository teamRepository) {

		super(eventPublisher);
		this.teamRepository = teamRepository;
	}

	public Page<BriefTeamResponseDto> getAll(Pageable pageable) {
		return teamRepository
				.findAll(pageable)
				.map(BriefTeamResponseDto::new);
	}

	public Page<BriefTeamResponseDto> getAllByIssuer(Pageable pageable, UUID issuerId) {
		return teamRepository
				.findAllByIssuerId(pageable, issuerId)
				.map(BriefTeamResponseDto::new);
	}

	public BriefTeamResponseDto getById(UUID id) {
		return teamRepository
				.findById(id)
				.map(BriefTeamResponseDto::new)
				.orElseThrow(() -> {
					logger.error("Team with id '{}' not found", id);
					throw new EntityNotFoundException(String.format("Team with id '%s' not found", id));
				});
	}

	public BriefTeamResponseDto create(CreateTeamRequestDto requestDto, UUID issuerId) {
		var team = new Team(requestDto);

		requestDto.employeesId()
				.forEach(employeeId -> team.addEmployee(new Employee(employeeId)));

		requestDto.projectsId()
				.forEach(projectId -> team.addProject(new Project(projectId)));

		var issuer = new Employee(issuerId);

		team.setCreator(issuer);
		team.addEmployee(issuer);

		teamRepository.save(team);
		logger.info("Team created with name '{}' by '{}", team.getName(), issuerId);

		eventPublisher.publishEvent(
				new SystemCommitOcurredEvent(
						String.format("Team created with name '%s'", team.getName()),
						CommitType.CREATE,
						issuer));

		return new BriefTeamResponseDto(team);
	}

	public void deleteById(UUID id, UUID issuerId) {
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
				new SystemCommitOcurredEvent(
						String.format("Team with id '%s' deactivated", id.toString()),
						CommitType.DEACTIVATE,
						new Employee(issuerId)));
	}

	public BriefTeamResponseDto updateById(UUID id, UpdateTeamRequestDto requestDto, UUID issuerId) {
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
				.ifPresent(team::setName);

		requestDto
				.description()
				.ifPresent(team::setDescription);

		teamRepository.save(team);
		eventPublisher.publishEvent(
				new SystemCommitOcurredEvent(
						String.format("Team with id '%s' updated", id.toString()),
						CommitType.UPDATE,
						new Employee(issuerId)));

		return new BriefTeamResponseDto(team);
	}

	public BriefTeamResponseDto addEmployee(UUID id, UUID employeeId, UUID issuerId) {
		var team = teamRepository
				.findById(id)
				.orElseThrow(() -> {
					logger.error("Team with id '{}' not found to add employee '{}'", id, employeeId);
					throw new EntityNotFoundException(
							String.format("Team with id '%s' not found to add employee '%s'",
									id.toString(), employeeId.toString()));
				});

		if (!team.isEnabled()) {
			logger.error("Team with id '{}' is not able to add employee", id);
			throw new DomainException(String.format("Team with id '%s' is not able to add employee", id.toString()));
		}

		if (!team.addEmployee(new Employee(employeeId))) {
			logger.error("Could not add employee to team '{}'", team.getName());
			throw new DomainException(String.format("Could not add employee to team '%s'", team.getName()));
		}

		teamRepository.save(team);
		eventPublisher.publishEvent(
				new SystemCommitOcurredEvent(
						String.format("Team with id '%s' updated with new employee with id '%s'",
								id.toString(), employeeId.toString()),
						CommitType.UPDATE,
						new Employee(issuerId)));

		return new BriefTeamResponseDto(team);
	}

	public BriefTeamResponseDto removeEmployee(UUID id, UUID employeeId, UUID issuerId) {
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

		if (!team.removeEmployee(new Employee(employeeId))) {
			logger.error("Could not remove employee of team '{}'", team.getName());
			throw new DomainException(String.format("Could not remove employee of team '%s'", team.getName()));
		}

		teamRepository.save(team);
		eventPublisher.publishEvent(
				new SystemCommitOcurredEvent(
						String.format("Team with id '%s' update without employee with id '%s'",
								id.toString(), employeeId.toString()),
						CommitType.UPDATE,
						new Employee(issuerId)));

		return new BriefTeamResponseDto(team);
	}
}
