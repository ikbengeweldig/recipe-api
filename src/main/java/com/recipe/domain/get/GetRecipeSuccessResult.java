package com.recipe.domain.get;

import com.recipe.domain.core.Recipe;

public record GetRecipeSuccessResult(Recipe recipe) implements GetRecipeResult {

}
