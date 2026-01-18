package com.recipe.adapter.in.rest;

import com.recipe.api.model.RetrieveRecipeResponse;
import com.recipe.api.rest.RetrieveRecipeApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RetrieveRecipeRestAdapter implements RetrieveRecipeApi {

    @Override
    public ResponseEntity<RetrieveRecipeResponse> retrieve(UUID recipeId) {
        return null;
    }
}
