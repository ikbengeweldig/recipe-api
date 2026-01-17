package com.recipe.domain.port.in;

import com.recipe.domain.core.RecipeId;
import com.recipe.domain.remove.RemoveRecipeResult;

public interface RemoveRecipeUseCase {

    RemoveRecipeResult remove(RecipeId recipeId);
}
