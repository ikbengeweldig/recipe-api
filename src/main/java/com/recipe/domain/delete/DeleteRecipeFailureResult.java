package com.recipe.domain.delete;

import com.recipe.domain.core.RecipeException;

public record DeleteRecipeFailureResult(RecipeException recipeException) implements DeleteRecipeResult {

}
