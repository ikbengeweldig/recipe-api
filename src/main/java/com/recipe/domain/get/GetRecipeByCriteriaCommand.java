package com.recipe.domain.get;

import com.recipe.domain.core.IngredientId;
import lombok.Builder;

import java.util.Set;

@Builder
public record GetRecipeByCriteriaCommand(String name, Boolean isVegetarian, Integer servings, Set<IngredientId> includingIngredients, Set<IngredientId> excludingIngredients,
                                         String instructions) {
}

