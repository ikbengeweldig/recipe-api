package com.recipe.domain.core;

import lombok.Getter;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
public record Recipe(RecipeId id, Integer servings, List<Ingredient> ingredients, String instructions) {

    public Recipe {

        Objects.requireNonNull(servings, "servings cannot be null");
        if (servings < 1) {
            throw new IllegalArgumentException("servings must be at least 1");
        }

        if (Objects.isNull(ingredients) || ingredients.isEmpty()) {
            throw new IllegalArgumentException("there has to be at least one ingredient");
        }

        if (Objects.isNull(instructions) || instructions.length() == 0 || instructions.replaceAll("\\s+", "").length() == 0) {
            throw new IllegalArgumentException("there must be some instructions");
        }

    }

    public boolean isVegetarian() {

        return ingredients.stream().allMatch(Ingredient::isVegetarian);
    }
}
