package com.recipe.adapter.in.rest;

import com.recipe.api.model.AddRecipeRequest;
import com.recipe.api.model.AddRecipeResponse;
import com.recipe.api.rest.AddRecipeApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecipeRestAdapter implements AddRecipeApi {

    @Override
    public ResponseEntity<AddRecipeResponse> add(AddRecipeRequest addRecipeRequest) {

        return null;
    }
}
