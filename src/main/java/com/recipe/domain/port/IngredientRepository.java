package com.recipe.domain.port;

import com.recipe.domain.core.Ingredient;

public interface IngredientRepository {

    Ingredient getOrCreateByName(String name);
}
