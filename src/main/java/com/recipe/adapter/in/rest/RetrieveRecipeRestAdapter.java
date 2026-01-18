package com.recipe.adapter.in.rest;

import com.recipe.adapter.in.rest.infrastructure.GetRecipeByCriteriaCommandMapper;
import com.recipe.adapter.in.rest.infrastructure.RetrieveRecipeResponseMapper;
import com.recipe.api.model.Recipe;
import com.recipe.api.model.RetrieveRecipeResponse;
import com.recipe.api.model.SearchRecipeRequest;
import com.recipe.api.model.SearchRecipeResponse;
import com.recipe.api.rest.RetrieveRecipeApi;
import com.recipe.domain.core.RecipeId;
import com.recipe.domain.get.GetRecipeByCriteriaCommand;
import com.recipe.domain.get.GetRecipeByCriteriaFailureResult;
import com.recipe.domain.get.GetRecipeByCriteriaResult;
import com.recipe.domain.get.GetRecipeByCriteriaSuccessResult;
import com.recipe.domain.get.GetRecipeByIdCommand;
import com.recipe.domain.get.GetRecipeFailureResult;
import com.recipe.domain.get.GetRecipeResult;
import com.recipe.domain.get.GetRecipeSuccessResult;
import com.recipe.domain.port.in.GetRecipeUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RetrieveRecipeRestAdapter implements RetrieveRecipeApi {

    private final GetRecipeUseCase getRecipeUseCase;
    private final RetrieveRecipeResponseMapper retrieveRecipeResponseMapper;
    private final GetRecipeByCriteriaCommandMapper getRecipeByCriteriaCommandMapper;

    @Override
    public ResponseEntity<RetrieveRecipeResponse> retrieve(UUID recipeId) {

        GetRecipeResult getRecipeResult = getRecipeUseCase.get(new GetRecipeByIdCommand(new RecipeId(recipeId)));

        return switch (getRecipeResult) {
            case GetRecipeSuccessResult successResult -> {
                RetrieveRecipeResponse retrieveRecipeResponse = retrieveRecipeResponseMapper.fromRecipe(successResult.recipe());
                yield ResponseEntity.ok(retrieveRecipeResponse);
            }
            case GetRecipeFailureResult failureResult -> throw failureResult.recipeException();
            default -> throw new IllegalStateException();
        };
    }

    @Override
    public ResponseEntity<SearchRecipeResponse> search(SearchRecipeRequest searchRecipeRequest) {

        GetRecipeByCriteriaCommand command = getRecipeByCriteriaCommandMapper.map(searchRecipeRequest);
        GetRecipeByCriteriaResult result = getRecipeUseCase.search(command);
        return switch (result) {
            case GetRecipeByCriteriaSuccessResult recipeByCriteriaSuccessResult -> {
                List<Recipe> recipes = recipeByCriteriaSuccessResult
                        .recipes()
                        .stream()
                        .map(retrieveRecipeResponseMapper::fromRecipe)
                        .map(Recipe.class::cast)
                        .toList();
                yield ResponseEntity.ok(SearchRecipeResponse
                                                .builder()
                                                .recipes(recipes)
                                                .build());
            }
            case GetRecipeByCriteriaFailureResult failureResult -> throw failureResult.recipeException();
            default -> throw new IllegalStateException();
        };
    }
}
