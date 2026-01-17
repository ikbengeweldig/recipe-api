package com.recipe.domain.port;

import com.recipe.domain.core.Recipe;
import com.recipe.domain.core.RecipeId;

public interface RecipeRepository {

    Recipe save(Recipe recipe);

    boolean remove(RecipeId recipeId);
}
