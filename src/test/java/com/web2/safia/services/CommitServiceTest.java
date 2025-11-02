package com.web2.safia.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.web2.safia.repositories.adapters.JpaCommitRepository;

public class CommitServiceTest {
	private CommitService underTest;

	@Mock
	private JpaCommitRepository commitRepository;

	@BeforeAll
	void setUp() {
		MockitoAnnotations.openMocks(this);
		underTest = new CommitService(commitRepository);
	}

	@Test
	void givenValidPageable_whenGetAll_thenReturnCommitPage() {
	}
}
