package com.recipe.domain.usecase.remove;

import java.util.UUID;

public record RemoveRecipeCommand(UUID recipeId) {
}
