package com.recipe.adapter.in.rest;

import com.recipe.adapter.in.rest.infrastructure.AddRecipeCommandMapper;
import com.recipe.adapter.in.rest.infrastructure.AddRecipeResponseMapper;
import com.recipe.api.model.AddRecipeRequest;
import com.recipe.api.model.AddRecipeResponse;
import com.recipe.api.rest.AddRecipeApi;
import com.recipe.domain.add.AddRecipeCommand;
import com.recipe.domain.add.AddRecipeFailureResult;
import com.recipe.domain.add.AddRecipeResult;
import com.recipe.domain.add.AddRecipeSuccessResult;
import com.recipe.domain.port.in.AddRecipeUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AddRecipeRestAdapter implements AddRecipeApi {

    private final AddRecipeUseCase addRecipeUseCase;
    private final AddRecipeCommandMapper addRecipeCommandMapper;
    private final AddRecipeResponseMapper addRecipeResponseMapper;

    @Override
    public ResponseEntity<AddRecipeResponse> add(AddRecipeRequest addRecipeRequest) {

        log.debug("received a recipe to add: {}", addRecipeRequest);

        AddRecipeCommand command = addRecipeCommandMapper.toCommand(addRecipeRequest);

        AddRecipeResult recipeResult = addRecipeUseCase.add(command);

        return switch (recipeResult) {
            case AddRecipeSuccessResult addRecipeSuccessResult -> ResponseEntity
                    .created(null)
                    .body(addRecipeResponseMapper.fromRecipe(addRecipeSuccessResult.recipe()));
            case AddRecipeFailureResult addRecipeFailureResult -> throw addRecipeFailureResult.recipeException();
            default -> throw new IllegalStateException();
        };
    }
}
