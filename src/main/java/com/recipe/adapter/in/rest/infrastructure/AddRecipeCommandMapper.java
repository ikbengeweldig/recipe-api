package com.recipe.adapter.in.rest.infrastructure;

import com.recipe.api.model.AddRecipeRequest;
import com.recipe.domain.add.AddRecipeCommand;
import org.mapstruct.Mapper;

@Mapper
public abstract class AddRecipeCommandMapper {

    public abstract AddRecipeCommand toCommand(AddRecipeRequest addRecipeRequest);
}
