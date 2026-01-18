package com.recipe.adapter.in.usecase;

import com.recipe.domain.core.Recipe;
import com.recipe.domain.core.RecipeException;
import com.recipe.domain.core.RecipeNotFoundException;
import com.recipe.domain.get.GetRecipeByCriteriaResult;
import com.recipe.domain.get.GetRecipeByCriteriaCommand;
import com.recipe.domain.get.GetRecipeByCriteriaFailureResult;
import com.recipe.domain.get.GetRecipeByCriteriaSuccessResult;
import com.recipe.domain.get.GetRecipeByIdCommand;
import com.recipe.domain.get.GetRecipeFailureResult;
import com.recipe.domain.get.GetRecipeResult;
import com.recipe.domain.get.GetRecipeSuccessResult;
import com.recipe.domain.port.in.GetRecipeUseCase;
import com.recipe.domain.port.out.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

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
                .orElseGet(() -> {
                    String message = "recipe not found with id: %s".formatted(getRecipeByIdCommand.recipeId());
                    return new GetRecipeFailureResult(new RecipeNotFoundException(message));
                });
    }

    @Override
    public GetRecipeByCriteriaResult search(GetRecipeByCriteriaCommand getRecipeByCriteriaCommand) {

        try {
            List<Recipe> search = recipeRepository.search(getRecipeByCriteriaCommand.name(),
                                                          getRecipeByCriteriaCommand.isVegetarian(),
                                                          getRecipeByCriteriaCommand.servings(),
                                                          getRecipeByCriteriaCommand.includingIngredients(),
                                                          getRecipeByCriteriaCommand.excludingIngredients(),
                                                          getRecipeByCriteriaCommand.instructions());
            return new GetRecipeByCriteriaSuccessResult(search);
        } catch (Exception e) {
            if (e instanceof RecipeException recipeException) {
                return new GetRecipeByCriteriaFailureResult(recipeException);
            }
            String message = "error happened while searching for recipes: %s".formatted(e.getMessage());
            return new GetRecipeByCriteriaFailureResult(new RecipeException(message));
        }
    }
}
