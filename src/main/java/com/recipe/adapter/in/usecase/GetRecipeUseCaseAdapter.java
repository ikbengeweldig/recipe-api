package com.recipe.adapter.in.usecase;

import com.recipe.domain.get.GetRecipeByIdCommand;
import com.recipe.domain.get.GetRecipeFailureResult;
import com.recipe.domain.get.GetRecipeResult;
import com.recipe.domain.get.GetRecipeSuccessResult;
import com.recipe.domain.port.in.GetRecipeUseCase;
import com.recipe.domain.port.out.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetRecipeUseCaseAdapter implements GetRecipeUseCase {

    private final RecipeRepository recipeRepository;

    @Override
    public GetRecipeResult get(GetRecipeByIdCommand getRecipeByIdCommand) {

        return recipeRepository
                .findRecipeById(getRecipeByIdCommand.recipeId())
                .map(GetRecipeSuccessResult::new)
                .map(GetRecipeResult.class::cast)
                .orElseGet(() -> new GetRecipeFailureResult("recipe was not found"));
    }
}
