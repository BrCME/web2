package com.web2.safia.project.internal;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web2.safia.commit.api.event.SystemCommitOcurred;
import com.web2.safia.project.api.dto.BriefProjectResponse;
import com.web2.safia.project.api.dto.CreateProjectRequest;
import com.web2.safia.project.api.dto.ProjectResponse;
import com.web2.safia.project.api.dto.UpdateProjectRequest;
import com.web2.safia.shared.base.BaseService;
import com.web2.safia.shared.entity.CommitType;
import com.web2.safia.shared.entity.Employee;
import com.web2.safia.shared.entity.Project;
import com.web2.safia.shared.entity.Team;
import com.web2.safia.shared.exception.DomainException;
import com.web2.safia.shared.exception.EntityNotFoundException;
import com.web2.safia.shared.exception.InputValidationException;

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

	public Page<ProjectResponse> getAll(Pageable pageable) {
		return projectRepository
				.findAll(pageable)
				.map(ProjectResponse::new);
	}

	public Page<ProjectResponse> getAllByIssuer(Pageable pageable, UUID issuerId) {
		return projectRepository
				.findAllByIssuerId(pageable, issuerId)
				.map(ProjectResponse::new);
	}

	public ProjectResponse getById(UUID id) {
		return projectRepository
				.findById(id)
				.map(ProjectResponse::new)
				.orElseThrow(() -> {
					logger.debug("Project with id '{}' not found", id);
					throw new EntityNotFoundException(String.format("Project with id '%s' not found", id));
				});
	}

	public BriefProjectResponse create(CreateProjectRequest request, UUID issuerId) {
		var project = new Project(request);

		var team = new Team(request.teamId());
		project.joinTeam(team);

		var issuer = new Employee(issuerId);
		project.setCreator(issuer);
		project.addEmployee(issuer);

		request
				.managerId()
				.ifPresent(managerId -> {
					var manager = new Employee(managerId);

					project.setManager(manager);
					project.addEmployee(manager);
				});

		projectRepository.save(project);
		logger.info("Project created with name '{}' by '{}'", project.getName(), issuerId);

		eventPublisher.publishEvent(
				new SystemCommitOcurred(
						String.format("Project created with name '%s'", project.getName()),
						CommitType.CREATE,
						issuer));

		return new BriefProjectResponse(project);
	}

	public void deleteById(UUID id, UUID issuerId) {
		var project = projectRepository
				.findById(id)
				.orElseThrow(() -> {
					logger.debug("Project with id '{}' not found to deactivate", id);
					throw new EntityNotFoundException(
							String.format("Project with id '%s' not found to deactivate", id.toString()));
				});

		project.setDeletedAt(LocalDateTime.now(ZoneOffset.UTC));
		projectRepository.save(project);

		eventPublisher.publishEvent(
				new SystemCommitOcurred(
						String.format("Project with id '%s' deactivated", id.toString()),
						CommitType.DEACTIVATE,
						new Employee(issuerId)));
	}

	public ProjectResponse updateById(UUID id, UpdateProjectRequest request, UUID issuerId) {
		var project = projectRepository
				.findById(id)
				.orElseThrow(() -> {
					logger.debug("Project with id '{}' not found to update", id);
					throw new EntityNotFoundException(
							String.format("Project with id '%s' not found to update", id.toString()));
				});

		if (!project.isEnabled()) {
			logger.debug("Project with id '{}' is not able to update", id);
			throw new DomainException(
					String.format("Project with id '%s' is not able to update", id.toString()));
		}

		project.setName(request.name());
		project.setDescription(request.description());

		request
				.managerId()
				.ifPresent(managerId -> project.setManager(new Employee(managerId)));

		request
				.teamId()
				.ifPresent(teamId -> project.joinTeam(new Team(teamId)));

		projectRepository.save(project);
		eventPublisher.publishEvent(
				new SystemCommitOcurred(
						String.format("Project with id '%s' updated", id.toString()),
						CommitType.UPDATE,
						new Employee(issuerId)));

		return new ProjectResponse(project);
	}

	public ProjectResponse addEmployee(UUID id, UUID employeeId, UUID issuerId) {
		var project = projectRepository
				.findById(id)
				.orElseThrow(() -> {
					logger.debug("Project with id '{}' not found to add employee '{}'", id, employeeId);
					throw new InputValidationException(
							String.format("Project with id '%s' not found to add employee '%s'",
									id.toString(), employeeId.toString()));
				});

		if (!project.addEmployee(new Employee(employeeId))) {
			logger.debug("Could not add employee to project '{}'", project.getName());
			throw new DomainException(String.format("Could not add employee to project '%s'", project.getName()));
		}

		projectRepository.save(project);
		eventPublisher.publishEvent(
				new SystemCommitOcurred(
						String.format("Project with id '%s' updated with new employee with id '%s'",
								id.toString(), employeeId.toString()),
						CommitType.UPDATE,
						new Employee(issuerId)));

		return new ProjectResponse(project);
	}

	public ProjectResponse removeEmployee(UUID id, UUID employeeId, UUID issuerId) {
		var project = projectRepository
				.findById(id)
				.orElseThrow(() -> {
					logger.debug("Project with id '{}' not found to remove employee '{}'", id, employeeId);
					throw new EntityNotFoundException(
							String.format("Project with id '%s' not found to remove employee '%s'",
									id.toString(), employeeId.toString()));
				});

		if (!project.isEnabled()) {
			logger.debug("Project with id '{}' is not able to remove employee", id);
			throw new EntityNotFoundException(
					String.format("Project with id '%s' is not able to remove employee", id.toString()));
		}

		if (!project.removeEmployee(new Employee(employeeId))) {
			logger.debug("Could not remove employee of project '{}'", project.getName());
			throw new DomainException(String.format("Could not remove employee of project '%s'", project.getName()));
		}

		projectRepository.save(project);
		eventPublisher.publishEvent(
				new SystemCommitOcurred(
						String.format("Project with id '%s' updated without employee with id '%s'",
								id.toString(), employeeId.toString()),
						CommitType.UPDATE,
						new Employee(issuerId)));

		return new ProjectResponse(project);
	}
}
