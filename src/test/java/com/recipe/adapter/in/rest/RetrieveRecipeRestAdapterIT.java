package com.recipe.adapter.in.rest;

import com.recipe.api.model.SearchRecipeRequest;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

class RetrieveRecipeRestAdapterIT extends AbstractTestBase<RetrieveRecipeRestAdapterIT.GivenStage, RetrieveRecipeRestAdapterIT.WhenStage, RetrieveRecipeRestAdapterIT.ThenStage> {

    private static final UUID GARLIC_ID = UUID.fromString("a8295570-a77a-411e-9193-4e2388bfec1f");

    @Test
    void successful_get_recipe() {

        given().i_have_a_recipe_to_add(REC_LENTILS_SOUP);
        when().i_add_my_new_recipe();
        then().recipe_has_an_id();

        when().i_retrieve_my_recipe_with_id()
              .and()
              .the_recipe_is_returned_successfully();

        then().recipe_has_an_id()
              .and()
              .ingredients_have_ids()
              .and()
              .recipe_has_name(REC_LENTILS_SOUP.getName())
              .and()
              .recipe_has_servings(REC_LENTILS_SOUP.getServings())
              .and()
              .recipe_has_instructions(REC_LENTILS_SOUP.getInstructions())
              .and()
              .recipe_has_ingredient(ING_LENTILS)
              .and()
              .recipe_has_ingredient(ING_TOMATO);
    }

    @Test
    void successful_search_recipe() {

        SearchRecipeRequest oilRequest = SearchRecipeRequest.builder()
                                                            .instructions("OIL")
                                                            .build();
        when().i_search_for_recipes(oilRequest)
              .and()
              .the_recipe_list_retrieved_successfully();

        then().recipe_list_has_size(1)
              .and()
              .recipe_list_has_one_with_name("Spaghetti Pomodoro");

        SearchRecipeRequest oilAndServingRequest = SearchRecipeRequest.builder()
                                                                      .instructions("OIL")
                                                                      .servings(2)
                                                                      .build();
        when().i_search_for_recipes(oilAndServingRequest)
              .and()
              .the_recipe_list_retrieved_successfully();

        then().recipe_list_has_size(1)
              .and()
              .recipe_list_has_one_with_name("Spaghetti Pomodoro");

        SearchRecipeRequest includingIngredientRequest = SearchRecipeRequest.builder()
                                                                     .includingIngredients(List.of(GARLIC_ID))
                                                                     .build();
        when().i_search_for_recipes(includingIngredientRequest)
              .and()
              .the_recipe_list_retrieved_successfully();

        then().recipe_list_has_size(3)
              .and()
              .recipe_list_has_one_with_name("Spaghetti Pomodoro")
              .and()
              .recipe_list_has_one_with_name("Chicken Curry")
              .and()
              .recipe_list_has_one_with_name("Veggie Chili");

        SearchRecipeRequest isVegetarianRequest = SearchRecipeRequest.builder()
                                                                     .isVegetarian(true)
                                                                     .build();
        when().i_search_for_recipes(isVegetarianRequest)
              .and()
              .the_recipe_list_retrieved_successfully();

        then().recipe_list_has_size(2)
              .and()
              .recipe_list_has_one_with_name("Spaghetti Pomodoro")
              .and()
              .recipe_list_has_one_with_name("Veggie Chili");
    }

    @JGivenStage
    public static class GivenStage extends AbstractTestBase.GivenStage<GivenStage> {
    }

    @JGivenStage
    public static class WhenStage extends AbstractTestBase.WhenStage<WhenStage> {
    }

    @JGivenStage
    public static class ThenStage extends AbstractTestBase.ThenStage<ThenStage> {
    }
}
