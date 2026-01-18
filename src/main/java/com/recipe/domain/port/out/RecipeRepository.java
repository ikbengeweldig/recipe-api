package com.recipe.domain.port.out;

import com.recipe.domain.core.Recipe;
import com.recipe.domain.core.RecipeId;

import java.util.Optional;

public interface RecipeRepository {

    Recipe save(Recipe recipe);

    boolean delete(RecipeId recipeId);

    Optional<Recipe> findRecipeById(RecipeId recipeId);
}
