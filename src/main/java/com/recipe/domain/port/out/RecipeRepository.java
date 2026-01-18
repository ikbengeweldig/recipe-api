package com.recipe.domain.port.out;

import com.recipe.domain.core.IngredientId;
import com.recipe.domain.core.Recipe;
import com.recipe.domain.core.RecipeId;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RecipeRepository {

    Recipe save(Recipe recipe);

    boolean delete(RecipeId recipeId);

    Optional<Recipe> findRecipeById(RecipeId recipeId);

    List<Recipe> search(String name, Boolean isVegetarian, Integer servings, Set<IngredientId> includingIngredients, Set<IngredientId> excludingIngredients, String instructions);
}
