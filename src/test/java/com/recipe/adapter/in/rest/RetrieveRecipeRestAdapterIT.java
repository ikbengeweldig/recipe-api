package com.recipe.adapter.in.rest;

import com.tngtech.jgiven.integration.spring.JGivenStage;
import org.junit.jupiter.api.Test;

class RetrieveRecipeRestAdapterIT extends AbstractTestBase<RetrieveRecipeRestAdapterIT.GivenStage, RetrieveRecipeRestAdapterIT.WhenStage, RetrieveRecipeRestAdapterIT.ThenStage> {

    @Test
    void successful_get_recipe() {

        given().i_have_a_recipe_to_add(REC_LENTILS_SOUP);
        when().i_add_my_new_recipe();
        then().recipe_has_an_id();

        when().i_retrieve_my_recipe_with_id();
        then()
                .recipe_has_an_id()
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


    @JGivenStage
    public static class GivenStage extends AbstractTestBase.GivenStage<GivenStage> {
    }

    @JGivenStage
    public static class WhenStage extends AbstractTestBase.WhenStage {
    }

    @JGivenStage
    public static class ThenStage extends AbstractTestBase.ThenStage<RetrieveRecipeRestAdapterIT.ThenStage> {
    }
}
