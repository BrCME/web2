package com.web2.safia.work.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web2.safia.commit.api.CreateCommitEvent;
import com.web2.safia.commit.api.CommitType;
import com.web2.safia.common.BaseService;
import com.web2.safia.employee.internal.Employee;
import com.web2.safia.work.api.BriefWorkResponseDto;
import com.web2.safia.work.api.CreateWorkRequestDto;

@Service
public class WorkService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(WorkService.class);

	private final JpaWorkRepository workRepository;

	public WorkService(
			ApplicationEventPublisher eventPublisher,
			JpaWorkRepository workRepository) {

		super(eventPublisher);
		this.workRepository = workRepository;
	}

	public Page<BriefWorkResponseDto> getAll(Pageable pageable) {
		return workRepository
				.findAll(pageable)
				.map(work -> new BriefWorkResponseDto(work));
	}

	public Page<BriefWorkResponseDto> getAllByIssuer(Pageable pageable, Employee creator) {
		return workRepository
				.findAllByEmployee(pageable, creator)
				.map(work -> new BriefWorkResponseDto(work));
	}

	public BriefWorkResponseDto create(CreateWorkRequestDto requestDto, Employee user) {
		// var actualTask = taskRepository.findById(task.getId());

		// if (!actualTask.isPresent()) {
		// logger.error("Atividade com id '{}' não encontrada", task.getId());
		// throw new InputValidationException(String.format("Atividade com id '%s' não
		// encontrada", task.getId()));
		// }
		// work.setTask(actualTask.get());

		var work = new Work(requestDto);

		// work.setEmployee(creator);
		workRepository.save(work);
		logger.info("Criado trabalho '{}' novo por '{}", work.getDescription(), user.getEmail());

		eventPublisher.publishEvent(
				new CreateCommitEvent(
						String.format("Criado trabalho '%s' novo", work.getDescription()),
						CommitType.CREATE,
						user));

		return new BriefWorkResponseDto(work);
	}
}
