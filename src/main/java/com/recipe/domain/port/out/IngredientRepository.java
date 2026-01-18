package com.recipe.domain.port.out;

import com.recipe.domain.core.Ingredient;

public interface IngredientRepository {

    Ingredient getOrCreateByName(Ingredient ingredient);
}
