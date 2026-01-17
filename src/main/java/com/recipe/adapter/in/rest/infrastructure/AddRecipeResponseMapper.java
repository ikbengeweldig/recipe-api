package com.recipe.adapter.in.rest.infrastructure;

import com.recipe.api.model.AddRecipeResponse;
import com.recipe.domain.core.Recipe;
import org.mapstruct.Mapper;

@Mapper(uses = {MapperService.class})
public abstract class AddRecipeResponseMapper {

    public abstract AddRecipeResponse fromRecipe(Recipe recipe);
}
