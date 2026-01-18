package com.recipe.adapter.out;

import com.recipe.adapter.out.infrastructure.jpa.RecipeJpaEntity;
import com.recipe.adapter.out.infrastructure.mapper.RecipeJpaEntityMapper;
import com.recipe.domain.core.IngredientId;
import com.recipe.domain.core.Recipe;
import com.recipe.domain.core.RecipeId;
import com.recipe.domain.port.out.RecipeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.recipe.adapter.out.RecipeJpaHelper.buildIdsQuery;
import static com.recipe.adapter.out.RecipeJpaHelper.setParameters;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static org.springframework.util.CollectionUtils.isEmpty;

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
    @Transactional
    public boolean delete(RecipeId recipeId) {

        return ofNullable(recipeId)
                .map(this::find)
                .map(entity -> {
                    entityManager.remove(entity);
                    return true;
                })
                .orElse(false);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Recipe> findRecipeById(RecipeId recipeId) {

        return ofNullable(find(recipeId)).map(recipeJpaEntityMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Recipe> search(String name,
                               Boolean isVegetarian,
                               Integer servings,
                               Set<IngredientId> includingIngredients,
                               Set<IngredientId> excludingIngredients,
                               String instructions) {

        String sql = buildIdsQuery(name, isVegetarian, servings, includingIngredients, excludingIngredients, instructions);

        Query query = entityManager.createNativeQuery(sql);
        setParameters(name, isVegetarian, servings, includingIngredients, excludingIngredients, instructions, query);


        List<UUID> ids = (List<UUID>) query.getResultList();

        if (isEmpty(ids)) {
            return emptyList();
        }

        List<RecipeJpaEntity> entities = entityManager
                .createQuery("""
                                     SELECT DISTINCT r
                                     FROM RecipeJpaEntity r
                                     LEFT JOIN FETCH r.ingredients
                                     WHERE r.id IN :IDS
                                     """, RecipeJpaEntity.class)
                .setParameter("IDS", ids)
                .getResultList();

        return entities
                .stream()
                .map(recipeJpaEntityMapper::toDomain)
                .toList();
    }

    private RecipeJpaEntity find(RecipeId recipeId) {

        return entityManager.find(RecipeJpaEntity.class, recipeId.value());
    }
}
