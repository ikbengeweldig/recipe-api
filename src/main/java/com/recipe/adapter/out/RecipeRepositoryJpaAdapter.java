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

@Repository
@RequiredArgsConstructor
public class RecipeRepositoryJpaAdapter implements RecipeRepository {

    private final EntityManager entityManager;

    private final RecipeJpaEntityMapper recipeJpaEntityMapper;

    @Override
    @Transactional
    public Recipe save(Recipe recipe) {

        RecipeJpaEntity toPersist = recipeJpaEntityMapper.fromDomain(recipe);
        entityManager.persist(toPersist);

        return recipeJpaEntityMapper.toDomain(toPersist);
    }

    @Override
    public boolean remove(RecipeId recipeId) {
        return false;
    }
}
