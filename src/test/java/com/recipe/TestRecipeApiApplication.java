package com.recipe;

import org.springframework.boot.SpringApplication;

public class TestRecipeApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(RecipeApiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
