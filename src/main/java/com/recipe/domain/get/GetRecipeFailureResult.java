package com.recipe.domain.get;

import com.recipe.domain.core.RecipeException;

public record GetRecipeFailureResult(RecipeException recipeException) implements GetRecipeResult {
}
