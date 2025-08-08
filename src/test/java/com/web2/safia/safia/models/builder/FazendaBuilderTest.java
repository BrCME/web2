package com.web2.safia.safia.models.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.web2.safia.safia.models.Farm;
import com.web2.safia.safia.models.builders.FazendaBuilder;

class FazendaBuilderTest {
	private FazendaBuilder underTest;

	@BeforeEach
	void setUp() {
		underTest = new FazendaBuilder();
	}

	@Test
	void givenValidArguments_whenBuilding_thenReturnExpectedFarm() {
		String validNome = "Fazenda Nova";

		Farm expected = new Farm(validNome);
		Farm result = underTest
				.builder()
				.withNome(validNome)
				.build();

		assertNotNull(result);
		assertEquals(expected.getNome(), result.getNome());
	}

	@Test
	void givenInvalidArguments_whenBuilding_thenThrowException() {
		String invalidNome = null;

		assertThrowsExactly(IllegalArgumentException.class, () -> underTest
				.builder()
				.withNome(invalidNome)
				.build());
	}

	@Test
	void givenEmptyArguments_whenBuilding_thenReturnEmptyFarm() {
		Farm result = underTest.builder().build();

		assertNotNull(result);
		assertNull(result.getNome());
	}
}
