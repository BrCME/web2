package com.web2.safia.project;

import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web2.safia.commit.Commit;
import com.web2.safia.commit.Commit.Type;
import com.web2.safia.commit.events.CreateCommitEvent;
import com.web2.safia.common.BaseService;
import com.web2.safia.employee.Employee;
import com.web2.safia.employee.events.GetEmployeeToAddByIdRequestEvent;
import com.web2.safia.employee.events.GetEmployeeToAddByIdResponseEvent;
import com.web2.safia.employee.events.GetEmployeeToRemoveByIdRequestEvent;
import com.web2.safia.employee.events.GetEmployeeToRemoveByIdResponseEvent;
import com.web2.safia.employee.events.GetManagerByIdRequestEvent;
import com.web2.safia.exceptions.DomainException;
import com.web2.safia.exceptions.EntityNotFoundException;
import com.web2.safia.exceptions.InputValidationException;
import com.web2.safia.project.dtos.BriefProjectResponseDto;
import com.web2.safia.project.dtos.CreateProjectRequestDto;
import com.web2.safia.project.dtos.ProjectResponseDto;
import com.web2.safia.project.dtos.UpdateProjectRequestDto;
import com.web2.safia.team.events.GetTeamByIdRequestEvent;

@Service
public class ProjectService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);

	private final JpaProjectRepository projectRepository;

	public ProjectService(
			ApplicationEventPublisher eventPublisher,
			JpaProjectRepository projectRepository) {

		super(eventPublisher);
		this.projectRepository = projectRepository;
	}

	public Page<ProjectResponseDto> getAll(Pageable pageable) {
		return projectRepository
				.findAll(pageable)
				.map(project -> new ProjectResponseDto(project));
	}

	public Page<ProjectResponseDto> getAllByIssuer(Pageable pageable, Employee user) {
		return projectRepository
				.findAllByCreator(pageable, user)
				.map(project -> new ProjectResponseDto(project));
	}

	public ProjectResponseDto getById(UUID id) throws EntityNotFoundException {
		return projectRepository
				.findById(id)
				.map(project -> new ProjectResponseDto(project))
				.orElseThrow(() -> {
					logger.error("Project with id '{}' not found", id);
					throw new EntityNotFoundException(String.format("Project with id '%s' not found", id));
				});
	}

	// @EventListener(classes = { GetTeamByIdRequestEvent.class })
	public BriefProjectResponseDto create(CreateProjectRequestDto requestDto, Employee user)
			throws InputValidationException {
		var project = new Project(requestDto);

		eventPublisher.publishEvent(new GetTeamByIdRequestEvent(requestDto.teamId()));
		// project.setTeam(team);

		requestDto
				.managerId()
				.ifPresent((managerId) -> {
					eventPublisher.publishEvent(new GetManagerByIdRequestEvent(managerId));
					// employeeRepository.findByEmail(project.getManager().getEmail());
					// project.setManager(manager);
					// project.addEmployee(manager);
				});

		project.setCreator(user);
		project.addEmployee(user);

		projectRepository.save(project);
		logger.info("Project created with name '{}' by '{}'", project.getName(), user.getEmail());

		eventPublisher.publishEvent(
				new CreateCommitEvent(
						String.format("Project created with name '%s'", project.getName()),
						Type.CREATE,
						user));

		return new BriefProjectResponseDto(project);
	}

	public void deleteById(UUID id, Employee user) throws EntityNotFoundException {
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
				new CreateCommitEvent(
						String.format("Project with id '%s' deactivated", id.toString()),
						Commit.Type.DEACTIVATE,
						user));
	}

	public ProjectResponseDto updateById(UUID id, UpdateProjectRequestDto requestDto, Employee user)
			throws DomainException, EntityNotFoundException {
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
				.ifPresent(managerId -> logger.info("Manager to update: {}", managerId));

		requestDto
				.teamId()
				.ifPresent(teamId -> logger.info("Team to update: {}", teamId));

		projectRepository.save(project);
		eventPublisher.publishEvent(
				new CreateCommitEvent(
						String.format("Project with id '%s' updated", id.toString()),
						Commit.Type.UPDATE,
						user));

		return new ProjectResponseDto(project);
	}

	// @EventListener(classes = { GetEmployeeToAddByIdResponseEvent.class })
	public ProjectResponseDto addEmployee(UUID id, UUID employeeId, Employee user) throws InputValidationException {
		var project = projectRepository
				.findById(id)
				.orElseThrow(() -> {
					logger.error("Project with id '{}' not found to add employee '{}'", id, employeeId);
					throw new InputValidationException(
							String.format("Project with id '%s' not found to add employee '%s'",
									id.toString(), employeeId.toString()));
				});

		eventPublisher.publishEvent(new GetEmployeeToAddByIdRequestEvent(employeeId));
		// var employee = employeeRepository.findByEmail(employeeEmail);

		projectRepository.save(project);
		eventPublisher.publishEvent(
				new CreateCommitEvent(
						String.format("Project with id '%s' updated with new employee with id '%s'",
								id.toString(), employeeId.toString()),
						Commit.Type.UPDATE,
						user));

		return new ProjectResponseDto(project);
	}

	// @EventListener(classes = { GetEmployeeToRemoveByIdResponseEvent.class })
	public ProjectResponseDto removeEmployee(UUID id, UUID employeeId, Employee user)
			throws DomainException, EntityNotFoundException {
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

		eventPublisher.publishEvent(new GetEmployeeToRemoveByIdRequestEvent(employeeId));
		// var employee = employeeRepository.findByEmail(employeeEmail);

		projectRepository.save(project);
		eventPublisher.publishEvent(
				new CreateCommitEvent(
						String.format("Project with id '%s' updated without employee with id '%s'",
								id.toString(), employeeId.toString()),
						Commit.Type.UPDATE,
						user));

		return new ProjectResponseDto(project);
	}
}
