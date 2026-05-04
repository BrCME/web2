package com.web2.safia.commit.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.web2.safia.commit.api.dto.CommitResponse;
import com.web2.safia.commit.api.event.SystemCommitOcurred;
import com.web2.safia.shared.entity.Commit;

@Service
public class CommitService {
	private static final Logger logger = LoggerFactory.getLogger(CommitService.class);

	private final CommitRepository commitRepository;

	public CommitService(CommitRepository commitRepository) {
		this.commitRepository = commitRepository;
	}

	public Page<CommitResponse> getAll(Pageable pageable) {
		return commitRepository
				.findAll(pageable)
				.map(CommitResponse::new);
	}

	@Async
	@EventListener
	public void onSystemCommitOcurredEvent(SystemCommitOcurred event) {
		logger.info("Occurred event: {}", event);

		var commit = new Commit(event);
		logger.info("Registered event: {}", commit);

		commitRepository.save(commit);
	}
}
