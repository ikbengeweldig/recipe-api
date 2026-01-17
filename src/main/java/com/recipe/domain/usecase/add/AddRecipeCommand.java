package com.recipe.domain.usecase.add;

import java.util.List;

public record AddRecipeCommand(String name, Integer servings, List<String> ingredients, String instructions) {
}
