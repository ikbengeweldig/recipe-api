package com.recipe.domain.delete;

import com.recipe.domain.core.RecipeId;

import static java.util.Objects.requireNonNull;

public record DeleteRecipeCommand(RecipeId recipeId) {

    public DeleteRecipeCommand {
        requireNonNull(recipeId, "name must not be null");
    }
}
