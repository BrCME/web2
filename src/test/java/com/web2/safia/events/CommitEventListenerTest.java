package com.web2.safia.events;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.web2.safia.models.Commit;
import com.web2.safia.models.builders.EmployeeBuilder;
import com.web2.safia.repositories.adapters.JpaCommitRepository;

@TestInstance(Lifecycle.PER_CLASS)
class CommitEventListenerTest {
	private CommitEventListener underTest;

	@Mock
	private JpaCommitRepository commitRepository;

	@BeforeAll
	void setUp() {
		MockitoAnnotations.openMocks(this);
		underTest = new CommitEventListener(commitRepository);
	}

	@Test
	void givenInvalidCommitEvent_whenOnCommitEvent_thenThrowsException() {
		assertThrows(IllegalArgumentException.class,
				() -> underTest.onCommitEvent(new CommitEvent(this, "Commit inválido", Commit.Type.ATIVACAO, null)));
	}

	@Test
	void givenValidCommitEvent_whenOnCommitEvent_thenReturnVoid() {
		var employeeBuilder = new EmployeeBuilder();

		var creator = employeeBuilder
				.builder()
				.withId(UUID.randomUUID())
				.build();

		var validCommitEvent = new CommitEvent(this, "Criado usuário 'user@mail.com' novo", Commit.Type.CRIACAO,
				creator);
		var validCommitModel = new Commit("Criado usuário 'user@mail.com' novo", Commit.Type.CRIACAO);

		when(commitRepository.save(validCommitModel)).thenReturn(validCommitModel);

		assertDoesNotThrow(() -> underTest.onCommitEvent(validCommitEvent));
	}
}
