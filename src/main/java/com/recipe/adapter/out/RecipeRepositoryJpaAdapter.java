package com.recipe.adapter.out;

import com.recipe.adapter.out.infrastructure.jpa.RecipeJpaEntity;
import com.recipe.adapter.out.infrastructure.mapper.RecipeJpaEntityMapper;
import com.recipe.domain.core.Recipe;
import com.recipe.domain.core.RecipeId;
import com.recipe.domain.port.out.RecipeRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Repository
@Transactional
@RequiredArgsConstructor
public class RecipeRepositoryJpaAdapter implements RecipeRepository {

    private final EntityManager entityManager;

    private final RecipeJpaEntityMapper recipeJpaEntityMapper;

    @Override
    public Recipe save(Recipe recipe) {

        RecipeJpaEntity toPersist = recipeJpaEntityMapper.fromDomain(recipe);
        entityManager.persist(toPersist);

        return recipeJpaEntityMapper.toDomain(toPersist);
    }

    @Override
    public boolean delete(RecipeId recipeId) {

        return Optional
                .ofNullable(recipeId)
                .map(this::find)
                .map(recipe -> {
                    entityManager.remove(recipe);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Optional<Recipe> findRecipeById(RecipeId recipeId) {

        return ofNullable(find(recipeId)).map(recipeJpaEntityMapper::toDomain);
    }

    private RecipeJpaEntity find(RecipeId recipeId) {

        return entityManager.find(RecipeJpaEntity.class, recipeId.value());
    }
}
