package com.recipe.adapter.in.rest.infrastructure;

import com.recipe.api.model.SearchRecipeRequest;
import com.recipe.domain.get.GetRecipeByCriteriaCommand;
import org.mapstruct.Mapper;

@Mapper(uses = MapperService.class)
public abstract class GetRecipeByCriteriaCommandMapper {

    public abstract GetRecipeByCriteriaCommand map(SearchRecipeRequest searchRecipeRequest);
}
