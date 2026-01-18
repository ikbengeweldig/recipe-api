package com.recipe.adapter.out;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RecipeJpaConstants {

    protected static final String SEARCH_IDS_QUERY_BASE = """
            SELECT DISTINCT rwvf.id
            FROM recipe_with_vegetarian_flag rwvf
            WHERE 1 = 1
            """;


    protected static final String FILTER_NAME = " AND to_tsvector('english', rwvf.name) @@ plainto_tsquery('english', :NAME)";

    protected static final String FILTER_INSTRUCTIONS = " AND to_tsvector('english', rwvf.instructions) @@ plainto_tsquery('english', :INSTRUCTIONS)";

    protected static final String FILTER_IS_VEGETARIAN = " AND rwvf.is_vegetarian = :IS_VEGETARIAN";

    protected static final String FILTER_SERVINGS = " AND rwvf.servings = :SERVINGS";

    protected static final String FILTER_INCLUDE_ANY = """
            AND EXISTS (
                SELECT 1
                FROM recipe_ingredient ri
                WHERE ri.recipe_id = rwvf.id
                  AND ri.ingredient_id IN (:INC_INGREDIENT_IDS)
            )
            """;

    protected static final String FILTER_EXCLUDE = """
            AND NOT EXISTS (
                SELECT 1
                FROM recipe_ingredient ri
                WHERE ri.recipe_id = rwvf.id
                  AND ri.ingredient_id IN (:EX_INGREDIENT_IDS)
            )
            """;
}
