package com.recipe.adapter.in.usecase;

import com.recipe.domain.core.RecipeException;
import com.recipe.domain.delete.DeleteRecipeCommand;
import com.recipe.domain.delete.DeleteRecipeFailureResult;
import com.recipe.domain.delete.DeleteRecipeResult;
import com.recipe.domain.delete.DeleteRecipeSuccessResult;
import com.recipe.domain.port.in.DeleteRecipeUseCase;
import com.recipe.domain.port.out.RecipeRepository;
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
            return deleted ? new DeleteRecipeSuccessResult() : new DeleteRecipeFailureResult(new RecipeException("deleted unsuccessful"));
        } catch (Exception ex) {
            if (ex instanceof RecipeException recipeException) {
                return new DeleteRecipeFailureResult(recipeException);
            }
            return new DeleteRecipeFailureResult(new RecipeException("delete unsuccessful, message: %s".formatted(ex.getMessage())));
        }
    }
}
