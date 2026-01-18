package com.recipe.domain.port.in;

import com.recipe.domain.core.RecipeId;
import com.recipe.domain.delete.DeleteRecipeCommand;
import com.recipe.domain.delete.DeleteRecipeResult;

public interface DeleteRecipeUseCase {

    DeleteRecipeResult delete(DeleteRecipeCommand deleteRecipeCommand);
}
