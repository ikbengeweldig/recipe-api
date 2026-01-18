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

import static java.util.Collections.emptyList;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static org.springframework.util.CollectionUtils.isEmpty;
import static org.springframework.util.StringUtils.hasText;

@Repository
@Transactional
@RequiredArgsConstructor
public class RecipeRepositoryJpaAdapter implements RecipeRepository {

    private static final String SEARCH_IDS_QUERY_BASE = """
            SELECT DISTINCT rwvf.id
            FROM recipe_with_vegetarian_flag rwvf
            WHERE 1 = 1
            """;


    private static final String FILTER_NAME = " AND to_tsvector('english', rwvf.name) @@ plainto_tsquery('english', :NAME)";

    private static final String FILTER_INSTRUCTIONS = " AND to_tsvector('english', rwvf.instructions) @@ plainto_tsquery('english', :INSTRUCTIONS)";

    private static final String FILTER_IS_VEGETARIAN = " AND rwvf.is_vegetarian = :IS_VEGETARIAN";

    private static final String FILTER_SERVINGS = " AND rwvf.servings = :SERVINGS";

    private static final String FILTER_INCLUDE_ANY = """
            AND EXISTS (
                SELECT 1
                FROM recipe_ingredient ri
                WHERE ri.recipe_id = rwvf.id
                  AND ri.ingredient_id IN (:INC_INGREDIENT_IDS)
            )
            """;

    private static final String FILTER_EXCLUDE = """
            AND NOT EXISTS (
                SELECT 1
                FROM recipe_ingredient ri
                WHERE ri.recipe_id = rwvf.id
                  AND ri.ingredient_id IN (:EX_INGREDIENT_IDS)
            )
            """;

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

    private String buildIdsQuery(String name,
                                 Boolean isVegetarian,
                                 Integer servings,
                                 Set<IngredientId> includingIngredients,
                                 Set<IngredientId> excludingIngredients,
                                 String instructions) {

        StringBuilder sql = new StringBuilder(SEARCH_IDS_QUERY_BASE);

        if (hasText(name)) {
            sql.append(FILTER_NAME);
        }

        if (nonNull(isVegetarian)) {
            sql.append(FILTER_IS_VEGETARIAN);
        }

        if (nonNull(servings)) {
            sql.append(FILTER_SERVINGS);
        }

        if (hasText(instructions)) {
            sql.append(FILTER_INSTRUCTIONS);
        }

        if (!isEmpty(includingIngredients)) {
            sql.append(FILTER_INCLUDE_ANY);
        }

        if (!isEmpty(excludingIngredients)) {
            sql.append(FILTER_EXCLUDE);
        }

        return sql.toString();
    }

    private void setParameters(String name,
                               Boolean isVegetarian,
                               Integer servings,
                               Set<IngredientId> includingIngredients,
                               Set<IngredientId> excludingIngredients,
                               String instructions,
                               Query nativeQuery) {


        if (hasText(name)) {
            nativeQuery.setParameter("NAME", name);
        }

        if (nonNull(isVegetarian)) {
            nativeQuery.setParameter("IS_VEGETARIAN", isVegetarian);
        }

        if (nonNull(servings)) {
            nativeQuery.setParameter("SERVINGS", servings);
        }

        if (hasText(instructions)) {
            nativeQuery.setParameter("INSTRUCTIONS", instructions);
        }

        if (!isEmpty(includingIngredients)) {
            nativeQuery.setParameter("INC_INGREDIENT_IDS",
                                     includingIngredients
                                             .stream()
                                             .map(IngredientId::value)
                                             .toList());
        }

        if (!isEmpty(excludingIngredients)) {
            nativeQuery.setParameter("EX_INGREDIENT_IDS",
                                     excludingIngredients
                                             .stream()
                                             .map(IngredientId::value)
                                             .toList());
        }
    }

    private RecipeJpaEntity find(RecipeId recipeId) {

        return entityManager.find(RecipeJpaEntity.class, recipeId.value());
    }
}
