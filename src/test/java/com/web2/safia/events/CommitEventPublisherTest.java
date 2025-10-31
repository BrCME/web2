package com.web2.safia.events;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doNothing;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import com.web2.safia.models.CommitType;
import com.web2.safia.models.Employee;

@TestInstance(Lifecycle.PER_CLASS)
class CommitEventPublisherTest {
	private CommitEventPublisher underTest;

	@Mock
	private ApplicationEventPublisher applicationEventPublisher;

	@BeforeAll
	void setUp() {
		MockitoAnnotations.openMocks(this);
		underTest = new CommitEventPublisher(applicationEventPublisher);
	}

	@Test
	void givenInvalidCommitEvent_whenPublishCommitEvent_thenReturnVoid() {
		assertDoesNotThrow(() -> underTest.publishCreateCommitEvent("Descrição inválida", null));
		assertDoesNotThrow(() -> underTest.publishCreateCommitEvent(null, new Employee()));
		assertDoesNotThrow(() -> underTest.publishCreateCommitEvent(null, null));
	}

	@Test
	void givenValidCommitEvent_whenPublishCreateCommitEvent_thenReturnVoid() {
		var creator = new Employee();

		var validCommitEvent = new CommitEvent(this, "Commit válido", CommitType.CRIACAO, creator);
		doNothing().when(applicationEventPublisher).publishEvent(validCommitEvent);

		assertDoesNotThrow(() -> underTest.publishCreateCommitEvent("Commit válido", creator));
	}

	@Test
	void givenValidCommitEvent_whenPublishActivateCommitEvent_thenReturnVoid() {
		var creator = new Employee();

		var validCommitEvent = new CommitEvent(this, "Commit válido", CommitType.ATIVACAO, creator);
		doNothing().when(applicationEventPublisher).publishEvent(validCommitEvent);

		assertDoesNotThrow(() -> underTest.publishActivateCommitEvent("Commit válido", creator));
	}

	@Test
	void givenValidCommitEvent_whenPublishDeactivateCommitEvent_thenReturnVoid() {
		var creator = new Employee();

		var validCommitEvent = new CommitEvent(this, "Commit válido", CommitType.DESATIVACAO, creator);
		doNothing().when(applicationEventPublisher).publishEvent(validCommitEvent);

		assertDoesNotThrow(() -> underTest.publishDeactivateCommitEvent("Commit válido", creator));
	}

	@Test
	void givenValidCommitEvent_whenPublishUpdateCommitEvent_thenReturnVoid() {
		var creator = new Employee();

		var validCommitEvent = new CommitEvent(this, "Commit válido", CommitType.ATUALIZACAO, creator);
		doNothing().when(applicationEventPublisher).publishEvent(validCommitEvent);

		assertDoesNotThrow(() -> underTest.publishUpdateCommitEvent("Commit válido", creator));
	}

	@Test
	void givenValidCommitEvent_whenPublishRemoveCommitEvent_thenReturnVoid() {
		var creator = new Employee();

		var validCommitEvent = new CommitEvent(this, "Commit válido", CommitType.REMOCAO, creator);
		doNothing().when(applicationEventPublisher).publishEvent(validCommitEvent);

		assertDoesNotThrow(() -> underTest.publishRemoveCommitEvent("Commit válido", creator));
	}
}
