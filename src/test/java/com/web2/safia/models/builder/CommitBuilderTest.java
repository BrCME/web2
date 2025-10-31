package com.web2.safia.models.builder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.web2.safia.models.CommitType;
import com.web2.safia.models.builders.CommitBuilder;

class CommitBuilderTest {
	private CommitBuilder underTest;

	@BeforeEach
	void setUp() {
		underTest = new CommitBuilder();
	}

	@Test
	void givenEmptyArguments_whenBuilding_thenReturnEmptyCommit() {
		var result = underTest
				.builder()
				.build();

		assertNotNull(result);
		assertNull(result.getId());
		assertNull(result.getType());
		assertNull(result.getDescription());
		assertNull(result.getCreator());
		assertNotNull(result.getCreatedAt());
		assertNull(result.getUpdatedAt());
		assertNull(result.getDeletedAt());
	}

	@Test
	void givenValidArguments_whenBuilding_thenReturnValidCommit() {
		var result = underTest
				.builder()
				.withType(CommitType.ATIVACAO)
				.withDescription("Descrição de teste")
				.build();

		assertNotNull(result);
		assertNotNull(result.getType());
		assertEquals(result.getType(), CommitType.ATIVACAO);
		assertNotNull(result.getDescription());
		assertEquals(result.getDescription(), "Descrição de teste");
		assertNotNull(result.getCreatedAt());
	}
}
