package com.recipe.adapter.out;

import com.recipe.domain.core.IngredientId;
import jakarta.persistence.Query;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;

import static com.recipe.adapter.out.RecipeJpaConstants.FILTER_EXCLUDE;
import static com.recipe.adapter.out.RecipeJpaConstants.FILTER_INCLUDE_ANY;
import static com.recipe.adapter.out.RecipeJpaConstants.FILTER_INSTRUCTIONS;
import static com.recipe.adapter.out.RecipeJpaConstants.FILTER_IS_VEGETARIAN;
import static com.recipe.adapter.out.RecipeJpaConstants.FILTER_NAME;
import static com.recipe.adapter.out.RecipeJpaConstants.FILTER_SERVINGS;
import static com.recipe.adapter.out.RecipeJpaConstants.SEARCH_IDS_QUERY_BASE;
import static java.util.Objects.nonNull;
import static org.springframework.util.CollectionUtils.isEmpty;
import static org.springframework.util.StringUtils.hasText;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RecipeJpaHelper {

    protected static String buildIdsQuery(String name,
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

    protected static void setParameters(String name,
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
            nativeQuery.setParameter("INC_INGREDIENT_IDS", includingIngredients.stream().map(IngredientId::value).toList());
        }

        if (!isEmpty(excludingIngredients)) {
            nativeQuery.setParameter("EX_INGREDIENT_IDS", excludingIngredients.stream().map(IngredientId::value).toList());
        }
    }
}
