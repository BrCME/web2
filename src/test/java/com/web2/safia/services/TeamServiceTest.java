package com.web2.safia.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.web2.safia.events.CommitEventPublisher;
import com.web2.safia.repositories.adapters.JpaEmployeeRepository;
import com.web2.safia.repositories.adapters.JpaTeamRepository;


class TeamServiceTest {
	private TeamService underTest;

	@Mock
	private CommitEventPublisher commitEventPublisher;
	
	@Mock
	private JpaTeamRepository teamRepository;

	@Mock
	private JpaEmployeeRepository employeeRepository;

	@BeforeAll
	void setUp() {
		MockitoAnnotations.openMocks(this);
		underTest = new TeamService(commitEventPublisher, teamRepository, employeeRepository);
	}

	@Test
	void givenValidPageable_whenGetAll_thenReturnTeamPage() {
	}

	@Test
	void givenValidPageable_whenGetAllByCreator_thenReturnTeamPage() {
	}

	@Test
	void create() {
	}

	@Test
	void deleteById() {
	}

	@Test
	void updateById() {
	}

	@Test
	void addEmployee() {
	}

	@Test
	void removeEmployee() {
	}
}
