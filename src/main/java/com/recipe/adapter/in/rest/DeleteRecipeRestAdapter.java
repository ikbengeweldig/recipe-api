package com.recipe.adapter.in.rest;

import com.recipe.api.rest.DeleteRecipeApi;
import com.recipe.domain.core.RecipeId;
import com.recipe.domain.delete.DeleteRecipeCommand;
import com.recipe.domain.delete.DeleteRecipeFailureResult;
import com.recipe.domain.delete.DeleteRecipeResult;
import com.recipe.domain.delete.DeleteRecipeSuccessResult;
import com.recipe.domain.port.in.DeleteRecipeUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DeleteRecipeRestAdapter implements DeleteRecipeApi {

    private final DeleteRecipeUseCase deleteRecipeUseCase;

    @Override
    public ResponseEntity<Void> delete(UUID recipeId) {

        DeleteRecipeResult deleteRecipeResult = deleteRecipeUseCase.delete(new DeleteRecipeCommand(new RecipeId(recipeId)));
        return switch (deleteRecipeResult) {
            case DeleteRecipeSuccessResult ignore -> ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
            case DeleteRecipeFailureResult ignore -> null;
            default -> throw new IllegalStateException();
        };
    }
}
