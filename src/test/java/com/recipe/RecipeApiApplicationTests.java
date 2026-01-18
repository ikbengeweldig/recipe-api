package com.recipe;

import com.recipe.configuration.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("it")
@Import(TestcontainersConfiguration.class)
class RecipeApiApplicationTests {

	@Test
	void contextLoads() {
	}
}
