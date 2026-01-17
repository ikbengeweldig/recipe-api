package com.recipe.domain.port.in;

import com.recipe.domain.add.AddRecipeCommand;
import com.recipe.domain.add.AddRecipeResult;

public interface AddRecipeUseCase {

    AddRecipeResult add(AddRecipeCommand addRecipeCommand);
}
