package com.recipe.domain.get;

import com.recipe.domain.core.RecipeException;

public record GetRecipeByCriteriaFailureResult(RecipeException recipeException) implements GetRecipeByCriteriaResult {
}
