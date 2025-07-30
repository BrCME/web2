package com.web2.sofia.sofia;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class SofiaApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void givenOne_whenAddNine_thenSumUpToTen() {
		Integer one = 1;
		Integer nine = 9;

		Assert.isTrue((1 + 9) == 10, "Numbers does not sum up to ten");
	}
}
