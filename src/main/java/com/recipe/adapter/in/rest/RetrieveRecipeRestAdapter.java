package com.recipe.adapter.in.rest;

import com.recipe.adapter.in.rest.infrastructure.RetrieveRecipeResponseMapper;
import com.recipe.api.model.RetrieveRecipeResponse;
import com.recipe.api.rest.RetrieveRecipeApi;
import com.recipe.domain.core.RecipeId;
import com.recipe.domain.get.GetRecipeByIdCommand;
import com.recipe.domain.get.GetRecipeFailureResult;
import com.recipe.domain.get.GetRecipeResult;
import com.recipe.domain.get.GetRecipeSuccessResult;
import com.recipe.domain.port.in.GetRecipeUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RetrieveRecipeRestAdapter implements RetrieveRecipeApi {

    private final GetRecipeUseCase getRecipeUseCase;
    private final RetrieveRecipeResponseMapper retrieveRecipeResponseMapper;

    @Override
    public ResponseEntity<RetrieveRecipeResponse> retrieve(UUID recipeId) {

        GetRecipeResult getRecipeResult = getRecipeUseCase.get(new GetRecipeByIdCommand(new RecipeId(recipeId)));

        return switch (getRecipeResult) {
            case GetRecipeSuccessResult successResult -> {
                RetrieveRecipeResponse retrieveRecipeResponse = retrieveRecipeResponseMapper.fromRecipe(successResult.recipe());
                yield ResponseEntity.ok(retrieveRecipeResponse);
            }
            case GetRecipeFailureResult failureResult -> null;
            default -> throw new IllegalStateException();
        };
    }
}
