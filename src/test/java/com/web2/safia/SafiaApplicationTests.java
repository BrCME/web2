package com.web2.safia;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;
import org.springframework.modulith.docs.Documenter.DiagramOptions;
import org.springframework.modulith.docs.Documenter.DiagramOptions.DiagramStyle;

@SpringBootTest
class SafiaApplicationTests {
	ApplicationModules modules = ApplicationModules.of(SafiaApplication.class);

	@Test
	void contextLoads() {
		assertNotNull(this.getClass());
	}

	@Test
	void verifiesModularStructure() {
		modules.verify();
	}

	@Test
	void generateUmlDocument() {
		var document = new Documenter(modules)
				.writeModulesAsPlantUml()
				.writeIndividualModulesAsPlantUml();

		assertNotNull(document);
	}

	@Test
	void generateUmlDiagram() {
		var diagram = DiagramOptions
				.defaults()
				.withStyle(DiagramStyle.UML);

		assertNotNull(diagram);
	}
}
