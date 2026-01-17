package com.recipe.adapter.in.usecase;

import com.recipe.domain.core.RecipeId;
import com.recipe.domain.port.in.RemoveRecipeUseCase;
import com.recipe.domain.port.out.RecipeRepository;
import com.recipe.domain.remove.RemoveRecipeFailureResult;
import com.recipe.domain.remove.RemoveRecipeResult;
import com.recipe.domain.remove.RemoveRecipeSuccessResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RemoveRecipeUseCaseAdapter implements RemoveRecipeUseCase {

    private final RecipeRepository recipeRepository;

    @Override
    public RemoveRecipeResult remove(RecipeId recipeId) {

        try {
            boolean remove = recipeRepository.remove(recipeId);
            return remove ? new RemoveRecipeSuccessResult() : new RemoveRecipeFailureResult("remove unsuccessful");
        } catch (Exception ex) {
            return new RemoveRecipeFailureResult("remove unsuccessful, reason: %s".formatted(ex.getMessage()));
        }
    }
}
