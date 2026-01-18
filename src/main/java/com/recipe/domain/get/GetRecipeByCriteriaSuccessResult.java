package com.recipe.domain.get;

import com.recipe.domain.core.Recipe;

import java.util.List;

public record GetRecipeByCriteriaSuccessResult(List<Recipe> recipes) implements GetRecipeByCriteriaResult {

}
