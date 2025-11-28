package com.web2.safia.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.web2.safia.models.builders.CommitBuilder;
import com.web2.safia.repositories.adapters.JpaCommitRepository;

@Component
public class CommitEventListener {
	private static final Logger logger = LoggerFactory.getLogger(CommitEventListener.class);

	private final JpaCommitRepository commitRepository;

	public CommitEventListener(JpaCommitRepository commitRepository) {
		this.commitRepository = commitRepository;
	}

	@Async
	@EventListener
	public void onCommitEvent(CommitEvent event) {
		var commitBuilder = new CommitBuilder();

		var commit = commitBuilder
			.builder()
			.withDescription(event.getDescription())
			.withType(event.getType())
			.withCreator(event.getCreator())
			.withCreatingAt(event.getCreatedAt())
			.build();
			
		logger.info("Evento ocorrido: {}", event);
		logger.info("Evento registrado: {}", commit);

		commitRepository.save(commit);
	}
}
