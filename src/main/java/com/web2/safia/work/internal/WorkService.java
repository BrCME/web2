package com.web2.safia.work.internal;

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
import com.web2.safia.shared.base.BaseService;
import com.web2.safia.shared.exception.DomainException;
import com.web2.safia.task.internal.Task;
import com.web2.safia.work.api.dto.BriefWorkResponseDto;
import com.web2.safia.work.api.dto.CreateWorkRequestDto;
import com.web2.safia.work.api.event.WorkFinishedEvent;

@Service
public class WorkService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(WorkService.class);

	private final WorkRepository workRepository;

	public WorkService(
			ApplicationEventPublisher eventPublisher,
			WorkRepository workRepository) {

		super(eventPublisher);
		this.workRepository = workRepository;
	}

	public Page<BriefWorkResponseDto> getAll(Pageable pageable) {
		return workRepository
				.findAll(pageable)
				.map(BriefWorkResponseDto::new);
	}

	public Page<BriefWorkResponseDto> getAllByIssuer(Pageable pageable, UUID issuerId) {
		return workRepository
				.findAllByIssuerId(pageable, issuerId)
				.map(BriefWorkResponseDto::new);
	}

	public BriefWorkResponseDto create(CreateWorkRequestDto requestDto, UUID issuerId) {
		var work = new Work(requestDto);

		work.setTask(new Task(requestDto.taskId()));

		var issuer = new Employee(issuerId);
		
		work.setEmployee(issuer);
		workRepository.save(work);
		logger.info("Criado trabalho '{}' novo por '{}", work.getDescription(), issuer.getEmail());

		eventPublisher.publishEvent(
				new SystemCommitOcurredEvent(
						String.format("Criado trabalho '%s' novo", work.getDescription()),
						CommitType.CREATE,
						issuer));

		return new BriefWorkResponseDto(work);
	}

	public void finish(UUID id) {
		var work = new Work(id);

		if (work.isFinished()) {
			logger.error("");
			throw new DomainException("");
		}

		work.finish();
		workRepository.save(work);

		eventPublisher.publishEvent(new WorkFinishedEvent(id));
	}
}
