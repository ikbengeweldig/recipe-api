package com.recipe.domain.add;

import com.recipe.domain.core.RecipeException;

public record AddRecipeFailureResult(RecipeException recipeException) implements AddRecipeResult {

}
