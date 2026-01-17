package com.recipe.domain.core.usecase;

import com.recipe.domain.usecase.add.AddRecipeCommand;
import com.recipe.domain.usecase.add.AddRecipeFailureResult;
import com.recipe.domain.usecase.add.AddRecipeResult;
import com.recipe.domain.usecase.add.AddRecipeSuccessResult;
import com.recipe.domain.core.Ingredient;
import com.recipe.domain.core.Recipe;
import com.recipe.domain.port.IngredientRepository;
import com.recipe.domain.port.RecipeRepository;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
public class AddRecipeUseCase {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    public AddRecipeResult add(AddRecipeCommand addRecipeCommand) {

        try {
            Objects.requireNonNull(addRecipeCommand, "command cannot be null");

            List<Ingredient> ingredients = ofNullable(addRecipeCommand).map(AddRecipeCommand::ingredients).map(Collection::stream).orElseGet(Stream::of).map(ingredientRepository::getOrCreateByName).toList();
            Recipe recipe = new Recipe(null, addRecipeCommand.servings(), ingredients, addRecipeCommand.instructions());

            Recipe persistedRecipe = recipeRepository.save(recipe);
            return new AddRecipeSuccessResult(persistedRecipe);
        } catch (Exception ex) {
            return new AddRecipeFailureResult("something went wrong, reason: %s".formatted(ex.getMessage()));
        }
    }
}
