package com.web2.safia.events;@Valid

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.web2.safia.commit.Commit;
import com.web2.safia.commit.CommitService;
import com.web2.safia.commit.JpaCommitRepository;
import com.web2.safia.commit.events.CreateCommitEvent;
import com.web2.safia.safia.employee.EmployeeBuilder;

@TestInstance(Lifecycle.PER_CLASS)
class CommitEventListenerTest {
	private CommitService underTest;

	@Mock
	private JpaCommitRepository commitRepository;

	@BeforeAll
	void setUp() {
		MockitoAnnotations.openMocks(this);
		underTest = new CommitService(commitRepository);
	}

	@Test
	void givenInvalidCommitEvent_whenOnCommitEvent_thenThrowsException() {
		assertThrows(IllegalArgumentException.class,
				() -> underTest.onCommitEvent(new CreateCommitEvent(this, "Commit inválido", Commit.Type.ATIVACAO, null)));
	}

	@Test
	void givenValidCommitEvent_whenOnCommitEvent_thenReturnVoid() {
		var employeeBuilder = new EmployeeBuilder();

		var creator = employeeBuilder
				.builder()
				.withId(UUID.randomUUID())
				.build();

		var validCommitEvent = new CreateCommitEvent(this, "Criado usuário 'user@mail.com' novo", Commit.Type.CRIACAO,
				creator);
		var validCommitModel = new Commit("Criado usuário 'user@mail.com' novo", Commit.Type.CRIACAO);

		when(commitRepository.save(validCommitModel)).thenReturn(validCommitModel);

		assertDoesNotThrow(() -> underTest.onCommitEvent(validCommitEvent));
	}
}
