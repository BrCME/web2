package com.web2.safia.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web2.safia.models.Commit;
import com.web2.safia.repositories.adapters.JpaCommitRepository;

@Service
public class CommitService {
	private static final Logger logger = LoggerFactory.getLogger(CommitServiceTest.class);

	private final JpaCommitRepository commitRepository;

	public CommitService(JpaCommitRepository commitRepository) {
		this.commitRepository = commitRepository;
	}

	public Page<Commit> getAll(Pageable pageable) {
		logger.info("Busca por todos os registros do histórico com paginação: {}", pageable);
		return commitRepository.findAll(pageable);
	}
}
