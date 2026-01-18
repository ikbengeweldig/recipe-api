package com.recipe.domain.port.in;

import com.recipe.domain.get.GetRecipeByIdCommand;
import com.recipe.domain.get.GetRecipeResult;

public interface GetRecipeUseCase {

    GetRecipeResult get(GetRecipeByIdCommand getRecipeByIdCommand);
}
