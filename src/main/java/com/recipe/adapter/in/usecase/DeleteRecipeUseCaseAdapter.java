package com.recipe.adapter.in.usecase;

import com.recipe.domain.core.RecipeId;
import com.recipe.domain.delete.DeleteRecipeCommand;
import com.recipe.domain.port.in.DeleteRecipeUseCase;
import com.recipe.domain.port.out.RecipeRepository;
import com.recipe.domain.delete.DeleteRecipeFailureResult;
import com.recipe.domain.delete.DeleteRecipeResult;
import com.recipe.domain.delete.DeleteRecipeSuccessResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteRecipeUseCaseAdapter implements DeleteRecipeUseCase {

    private final RecipeRepository recipeRepository;

    @Override
    public DeleteRecipeResult delete(DeleteRecipeCommand deleteRecipeCommand) {

        try {
            boolean deleted = recipeRepository.delete(deleteRecipeCommand.recipeId());
            return deleted ? new DeleteRecipeSuccessResult() : new DeleteRecipeFailureResult("deleted unsuccessful");
        } catch (Exception ex) {
            return new DeleteRecipeFailureResult("delete unsuccessful, message: %s".formatted(ex.getMessage()));
        }
    }
}
