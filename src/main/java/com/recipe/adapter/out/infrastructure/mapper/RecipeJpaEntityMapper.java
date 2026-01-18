package com.recipe.adapter.out.infrastructure.mapper;

import com.recipe.adapter.in.rest.infrastructure.MapperService;
import com.recipe.adapter.out.infrastructure.jpa.RecipeJpaEntity;
import com.recipe.adapter.out.infrastructure.jpa.RecipeViewJpaEntity;
import com.recipe.domain.core.Recipe;
import org.mapstruct.Mapper;

@Mapper(uses = {MapperService.class, IngredientJpaEntityMapper.class})
public abstract class RecipeJpaEntityMapper {

    public abstract RecipeJpaEntity fromDomain(Recipe recipe);

    public abstract Recipe toDomain(RecipeJpaEntity recipeJpaEntity);

    public abstract Recipe toDomain(RecipeViewJpaEntity recipeViewJpaEntity);
}
