package com.web2.safia.commit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.web2.safia.commit.dtos.CommitResponseDto;
import com.web2.safia.commit.events.CreateCommitEvent;

@Service
public class CommitService {
	private static final Logger logger = LoggerFactory.getLogger(CommitService.class);

	private final JpaCommitRepository commitRepository;

	public CommitService(JpaCommitRepository commitRepository) {
		this.commitRepository = commitRepository;
	}

	public Page<CommitResponseDto> getAll(Pageable pageable) {
		return commitRepository
				.findAll(pageable)
				.map(commit -> new CommitResponseDto(commit));
	}

	// @Async
	// @EventListener
	// public void onCreateCommitEvent(CreateCommitEvent event) {
	// 	logger.info("Occurred event: {}", event);

	// 	var commit = new Commit(event);
	// 	logger.info("Registered event: {}", commit);

	// 	commitRepository.save(commit);
	// }
}
