package com.web2.safia.project.internal;

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
import com.web2.safia.project.api.dto.BriefProjectResponseDto;
import com.web2.safia.project.api.dto.CreateProjectRequestDto;
import com.web2.safia.project.api.dto.ProjectResponseDto;
import com.web2.safia.project.api.dto.UpdateProjectRequestDto;
import com.web2.safia.shared.base.BaseService;
import com.web2.safia.shared.exception.DomainException;
import com.web2.safia.shared.exception.EntityNotFoundException;
import com.web2.safia.shared.exception.InputValidationException;
import com.web2.safia.team.internal.Team;

@Service
public class ProjectService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);

	private final ProjectRepository projectRepository;

	public ProjectService(
			ApplicationEventPublisher eventPublisher,
			ProjectRepository projectRepository) {

		super(eventPublisher);
		this.projectRepository = projectRepository;
	}

	public Page<ProjectResponseDto> getAll(Pageable pageable) {
		return projectRepository
				.findAll(pageable)
				.map(ProjectResponseDto::new);
	}

	public Page<ProjectResponseDto> getAllByIssuer(Pageable pageable, UUID issuerId) {
		return projectRepository
				.findAllByIssuerId(pageable, issuerId)
				.map(ProjectResponseDto::new);
	}

	public ProjectResponseDto getById(UUID id) {
		return projectRepository
				.findById(id)
				.map(ProjectResponseDto::new)
				.orElseThrow(() -> {
					logger.error("Project with id '{}' not found", id);
					throw new EntityNotFoundException(String.format("Project with id '%s' not found", id));
				});
	}

	public BriefProjectResponseDto create(CreateProjectRequestDto requestDto, UUID issuerId) {
		var project = new Project(requestDto);

		var team = new Team(requestDto.teamId());
		project.setTeam(team);

		var issuer = new Employee(issuerId);
		project.setCreator(issuer);
		project.addEmployee(issuer);

		requestDto
				.managerId()
				.ifPresent(managerId -> {
					var manager = new Employee(managerId);

					project.setManager(manager);
					project.addEmployee(manager);
				});

		projectRepository.save(project);
		logger.info("Project created with name '{}' by '{}'", project.getName(), issuerId);

		eventPublisher.publishEvent(
				new SystemCommitOcurredEvent(
						String.format("Project created with name '%s'", project.getName()),
						CommitType.CREATE,
						issuer));

		return new BriefProjectResponseDto(project);
	}

	public void deleteById(UUID id, UUID issuerId) {
		var project = projectRepository
				.findById(id)
				.orElseThrow(() -> {
					logger.error("Project with id '{}' not found to deactivate", id);
					throw new EntityNotFoundException(
							String.format("Project with id '%s' not found to deactivate", id.toString()));
				});

		project.setDeletedAt(LocalDateTime.now());
		projectRepository.save(project);

		eventPublisher.publishEvent(
				new SystemCommitOcurredEvent(
						String.format("Project with id '%s' deactivated", id.toString()),
						CommitType.DEACTIVATE,
						new Employee(issuerId)));
	}

	public ProjectResponseDto updateById(UUID id, UpdateProjectRequestDto requestDto, UUID issuerId) {
		var project = projectRepository
				.findById(id)
				.orElseThrow(() -> {
					logger.error("Project with id '{}' not found to update", id);
					throw new EntityNotFoundException(
							String.format("Project with id '%s' not found to update", id.toString()));
				});

		if (!project.isEnabled()) {
			logger.error("Project with id '{}' is not able to update", id);
			throw new DomainException(
					String.format("Project with id '%s' is not able to update", id.toString()));
		}

		project.setName(requestDto.name());
		project.setDescription(requestDto.description());

		requestDto
				.managerId()
				.ifPresent(managerId -> project.setManager(new Employee(managerId)));

		requestDto
				.teamId()
				.ifPresent(teamId -> project.setTeam(new Team(teamId)));

		projectRepository.save(project);
		eventPublisher.publishEvent(
				new SystemCommitOcurredEvent(
						String.format("Project with id '%s' updated", id.toString()),
						CommitType.UPDATE,
						new Employee(issuerId)));

		return new ProjectResponseDto(project);
	}

	public ProjectResponseDto addEmployee(UUID id, UUID employeeId, UUID issuerId) {
		var project = projectRepository
				.findById(id)
				.orElseThrow(() -> {
					logger.error("Project with id '{}' not found to add employee '{}'", id, employeeId);
					throw new InputValidationException(
							String.format("Project with id '%s' not found to add employee '%s'",
									id.toString(), employeeId.toString()));
				});

		if (!project.addEmployee(new Employee(employeeId))) {
			logger.error("Could not add employee to project '{}'", project.getName());
			throw new DomainException(String.format("Could not add employee to project '%s'", project.getName()));
		}

		projectRepository.save(project);
		eventPublisher.publishEvent(
				new SystemCommitOcurredEvent(
						String.format("Project with id '%s' updated with new employee with id '%s'",
								id.toString(), employeeId.toString()),
						CommitType.UPDATE,
						new Employee(issuerId)));

		return new ProjectResponseDto(project);
	}

	public ProjectResponseDto removeEmployee(UUID id, UUID employeeId, UUID issuerId) {
		var project = projectRepository
				.findById(id)
				.orElseThrow(() -> {
					logger.error("Project with id '{}' not found to remove employee '{}'", id, employeeId);
					throw new EntityNotFoundException(
							String.format("Project with id '%s' not found to remove employee '%s'",
									id.toString(), employeeId.toString()));
				});

		if (!project.isEnabled()) {
			logger.error("Project with id '{}' is not able to remove employee", id);
			throw new EntityNotFoundException(
					String.format("Project with id '%s' is not able to remove employee", id.toString()));
		}

		if (!project.removeEmployee(new Employee(employeeId))) {
			logger.error("Could not remove employee of project '{}'", project.getName());
			throw new DomainException(String.format("Could not remove employee of project '%s'", project.getName()));
		}

		projectRepository.save(project);
		eventPublisher.publishEvent(
				new SystemCommitOcurredEvent(
						String.format("Project with id '%s' updated without employee with id '%s'",
								id.toString(), employeeId.toString()),
						CommitType.UPDATE,
						new Employee(issuerId)));

		return new ProjectResponseDto(project);
	}
}
