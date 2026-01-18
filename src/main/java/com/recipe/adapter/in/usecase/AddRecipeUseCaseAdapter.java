package com.recipe.adapter.in.usecase;

import com.recipe.domain.add.AddRecipeCommand;
import com.recipe.domain.add.AddRecipeFailureResult;
import com.recipe.domain.add.AddRecipeResult;
import com.recipe.domain.add.AddRecipeSuccessResult;
import com.recipe.domain.core.Ingredient;
import com.recipe.domain.core.Recipe;
import com.recipe.domain.core.RecipeException;
import com.recipe.domain.port.in.AddRecipeUseCase;
import com.recipe.domain.port.out.IngredientRepository;
import com.recipe.domain.port.out.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;

@Component
@RequiredArgsConstructor
public class AddRecipeUseCaseAdapter implements AddRecipeUseCase {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    @Override
    public AddRecipeResult add(AddRecipeCommand addRecipeCommand) {

        try {
            Objects.requireNonNull(addRecipeCommand, "command cannot be null");

            List<Ingredient> ingredients = ofNullable(addRecipeCommand)
                    .map(AddRecipeCommand::ingredients)
                    .map(Collection::stream)
                    .orElseGet(Stream::of)
                    .map(arg -> new Ingredient(null, arg.name(), arg.isVegetarian()))
                    .map(ingredientRepository::getOrCreateByName)
                    .toList();

            Recipe recipe = new Recipe(null, addRecipeCommand.name(), addRecipeCommand.servings(), ingredients, addRecipeCommand.instructions());

            Recipe persistedRecipe = recipeRepository.save(recipe);
            return new AddRecipeSuccessResult(persistedRecipe);
        } catch (Exception ex) {
            return new AddRecipeFailureResult(new RecipeException("something went wrong, message: %s".formatted(ex.getMessage())));
        }
    }
}
