package com.recipe.domain.usecase.add;

import com.recipe.domain.core.Recipe;

public record AddRecipeSuccessResult(Recipe recipe) implements AddRecipeResult {

}
