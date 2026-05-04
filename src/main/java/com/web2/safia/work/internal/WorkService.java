package com.web2.safia.work.internal;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web2.safia.commit.api.event.SystemCommitOcurred;
import com.web2.safia.shared.base.BaseService;
import com.web2.safia.shared.entity.CommitType;
import com.web2.safia.shared.entity.Employee;
import com.web2.safia.shared.entity.Task;
import com.web2.safia.shared.entity.Work;
import com.web2.safia.shared.exception.DomainException;
import com.web2.safia.work.api.dto.BriefWorkResponse;
import com.web2.safia.work.api.dto.CreateWorkRequest;
import com.web2.safia.work.api.event.WorkFinished;

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

	public Page<BriefWorkResponse> getAll(Pageable pageable) {
		return workRepository
				.findAll(pageable)
				.map(BriefWorkResponse::new);
	}

	public Page<BriefWorkResponse> getAllByIssuer(Pageable pageable, UUID issuerId) {
		return workRepository
				.findAllByIssuerId(pageable, issuerId)
				.map(BriefWorkResponse::new);
	}

	public BriefWorkResponse create(CreateWorkRequest request, UUID issuerId) {
		var work = new Work(request);

		work.setTask(new Task(request.taskId()));

		var issuer = new Employee(issuerId);

		work.setEmployee(issuer);
		workRepository.save(work);
		logger.debug("New Work '{}' created by '{}", work.getDescription(), issuer.getEmail());

		eventPublisher.publishEvent(
				new SystemCommitOcurred(
						String.format("New Work '%s' created", work.getDescription()),
						CommitType.CREATE,
						issuer));

		return new BriefWorkResponse(work);
	}

	public void finish(UUID id, UUID issuerId) {
		var work = new Work(id);

		if (work.isFinished()) {
			logger.debug("");
			throw new DomainException("");
		}

		work.finish();
		workRepository.save(work);

		eventPublisher.publishEvent(new WorkFinished(id, issuerId));
	}
}
