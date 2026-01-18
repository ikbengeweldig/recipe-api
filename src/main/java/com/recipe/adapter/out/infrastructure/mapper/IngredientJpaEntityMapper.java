package com.recipe.adapter.out.infrastructure.mapper;

import com.recipe.adapter.in.rest.infrastructure.MapperService;
import com.recipe.adapter.out.infrastructure.jpa.IngredientJpaEntity;
import com.recipe.domain.core.Ingredient;
import org.mapstruct.Mapper;

@Mapper(uses = MapperService.class)
public abstract class IngredientJpaEntityMapper {

    public abstract IngredientJpaEntity map(Ingredient ingredient);
}
