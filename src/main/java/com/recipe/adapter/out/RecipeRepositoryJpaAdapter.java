package com.recipe.adapter.out;

import com.recipe.domain.core.Recipe;
import com.recipe.domain.core.RecipeId;
import com.recipe.domain.port.out.RecipeRepository;
import org.springframework.stereotype.Repository;

@Repository
public class RecipeRepositoryJpaAdapter implements RecipeRepository {

    @Override
    public Recipe save(Recipe recipe) {
        return null;
    }

    @Override
    public boolean remove(RecipeId recipeId) {
        return false;
    }
}
