package com.recipe.adapter.in.rest.infrastructure;

import com.recipe.domain.core.AbstractValue;
import com.recipe.domain.core.IngredientId;
import com.recipe.domain.core.RecipeId;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Component
public class MapperService {

    public UUID map(RecipeId recipeId) {

        return ofNullable(recipeId)
                .map(AbstractValue::value)
                .orElse(null);
    }

    public UUID map(IngredientId ingredientId) {

        return ofNullable(ingredientId)
                .map(AbstractValue::value)
                .orElse(null);
    }

    public RecipeId mapToRecipeId(UUID id) {

        return ofNullable(id)
                .map(RecipeId::new)
                .orElse(null);
    }

    public IngredientId mapToIngredientId(UUID id) {

        return ofNullable(id)
                .map(IngredientId::new)
                .orElse(null);
    }
}
