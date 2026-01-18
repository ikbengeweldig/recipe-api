package com.recipe.domain.add;

import java.util.List;

import static java.util.Objects.requireNonNull;

public record AddRecipeCommand(String name, Integer servings, List<IngredientInput> ingredients, String instructions) {

    public AddRecipeCommand {
        requireNonNull(name, "name must not be null");
        requireNonNull(servings, "servings must not be null");
        requireNonNull(ingredients, "ingredients must not be null");
        requireNonNull(instructions, "instructions must not be null");
    }

    public record IngredientInput(String name, Boolean isVegetarian) {
        public IngredientInput {
            requireNonNull(name, "ingredient.name must not be null");
            requireNonNull(isVegetarian, "ingredient.isVegetarian must not be null");
        }
    }
}

