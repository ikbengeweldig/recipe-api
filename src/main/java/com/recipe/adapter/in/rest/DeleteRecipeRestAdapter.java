package com.recipe.adapter.in.rest;

import com.recipe.api.rest.RemoveRecipeApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DeleteRecipeRestAdapter implements RemoveRecipeApi {

    @Override
    public ResponseEntity<Void> remove(UUID recipeId) {
        return null;
    }
}
