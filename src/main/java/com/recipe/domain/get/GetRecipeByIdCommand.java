package com.recipe.domain.get;

import com.recipe.domain.core.RecipeId;

import static java.util.Objects.requireNonNull;

public record GetRecipeByIdCommand(RecipeId recipeId) {

    public GetRecipeByIdCommand {
        requireNonNull(recipeId, "recipeId must not be null");
    }
}

