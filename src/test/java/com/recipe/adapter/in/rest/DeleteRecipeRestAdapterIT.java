package com.recipe.adapter.in.rest;

import com.tngtech.jgiven.integration.spring.JGivenStage;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class DeleteRecipeRestAdapterIT extends AbstractTestBase<DeleteRecipeRestAdapterIT.GivenStage, DeleteRecipeRestAdapterIT.WhenStage, DeleteRecipeRestAdapterIT.ThenStage> {

    @Test
    void successful_delete_recipe() {

        given().i_have_a_recipe_to_add(REC_LENTILS_SOUP);
        when().i_add_my_new_recipe();
        then().recipe_has_an_id();

        when().i_delete_my_recipe();
        then().http_status_is(HttpStatus.NO_CONTENT);

        when()
                .i_retrieve_my_recipe_with_id()
                .and()
                .the_recipe_is_not_found();
    }


    @JGivenStage
    public static class GivenStage extends AbstractTestBase.GivenStage<DeleteRecipeRestAdapterIT.GivenStage> {
    }

    @JGivenStage
    public static class WhenStage extends AbstractTestBase.WhenStage<DeleteRecipeRestAdapterIT.WhenStage> {
    }

    @JGivenStage
    public static class ThenStage extends AbstractTestBase.ThenStage<DeleteRecipeRestAdapterIT.ThenStage> {
    }
}
