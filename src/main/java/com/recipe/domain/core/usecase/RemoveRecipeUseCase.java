package com.recipe.domain.core.usecase;

import com.recipe.domain.core.RecipeId;
import com.recipe.domain.port.RecipeRepository;
import com.recipe.domain.usecase.remove.RemoveRecipeFailureResult;
import com.recipe.domain.usecase.remove.RemoveRecipeResult;
import com.recipe.domain.usecase.remove.RemoveRecipeSuccessResult;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RemoveRecipeUseCase {

    private final RecipeRepository recipeRepository;

    public RemoveRecipeResult remove(RecipeId recipeId) {

        try {
            boolean remove = recipeRepository.remove(recipeId);
            return remove ? new RemoveRecipeSuccessResult() : new RemoveRecipeFailureResult("remove unsuccessful");
        } catch (Exception ex) {
            return new RemoveRecipeFailureResult("remove unsuccessful, reason: %s".formatted(ex.getMessage()));
        }
    }
}
