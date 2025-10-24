package com.web2.safia.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.web2.safia.models.Commit;
import com.web2.safia.repositories.adapters.JpaCommitRepository;

@Component
public class CommitEventListener {
	private static final Logger logger = LoggerFactory.getLogger(CommitEventListener.class);
	
	private final JpaCommitRepository commitRepository;

	public CommitEventListener(JpaCommitRepository commitRepository) {
		this.commitRepository = commitRepository;
	}
	
	@EventListener
	@Async
	public void onApplicationEvent(CommitEvent event) {
		var commit = new Commit(event.getDescription(), event.getType());
		commit.setCreator(event.getCreator().getId());
		commit.setCreatedAt(event.getCreatedAt());

		logger.info("Evento disparado: {}", commit);

		commitRepository.save(commit);
	}
}
