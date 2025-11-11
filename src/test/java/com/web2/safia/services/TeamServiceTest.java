package com.web2.safia.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.web2.safia.events.CommitEventPublisher;
import com.web2.safia.repositories.adapters.JpaTeamRepository;

public class TeamServiceTest {
	private TeamService underTest;

	@Mock
	private CommitEventPublisher commitEventPublisher;
	
	@Mock
	private JpaTeamRepository teamRepository;

	@BeforeAll
	void setUp() {
		MockitoAnnotations.openMocks(this);
		underTest = new TeamService(commitEventPublisher, teamRepository);
	}

	@Test
	void givenValidPageable_whenGetAll_thenReturnTeamPage() {
	}
}
