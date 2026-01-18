package com.recipe.adapter.in.rest.infrastructure;

import com.recipe.api.model.RetrieveRecipeResponse;
import com.recipe.domain.core.Recipe;
import org.mapstruct.Mapper;

@Mapper(uses = {MapperService.class})
public abstract class RetrieveRecipeResponseMapper {

    public abstract RetrieveRecipeResponse fromRecipe(Recipe recipe);
}
