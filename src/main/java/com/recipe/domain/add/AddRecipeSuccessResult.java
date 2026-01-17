package com.recipe.domain.add;

import com.recipe.domain.core.Recipe;

public record AddRecipeSuccessResult(Recipe recipe) implements AddRecipeResult {

}
