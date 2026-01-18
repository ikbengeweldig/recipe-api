package com.recipe.adapter.in.rest;

import com.recipe.api.model.AddRecipeRequest;
import com.recipe.api.model.AddRecipeResponse;
import com.recipe.api.model.Ingredient;
import com.recipe.api.rest.AddRecipeApi;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class AddRecipeRestAdapterIT extends AbstractTestBase<AddRecipeRestAdapterIT.GivenStage, AddRecipeRestAdapterIT.WhenStage, AddRecipeRestAdapterIT.ThenStage> {

    @Test
    void successful_add_recipe() throws Exception {

        Ingredient lentils = Ingredient
                .builder()
                .name("lentils")
                .isVegetarian(true)
                .build();
        Ingredient tomato = Ingredient
                .builder()
                .name("tomato")
                .isVegetarian(true)
                .build();
        List<Ingredient> ingredients = List.of(lentils, tomato);

        AddRecipeRequest addRecipeRequest = AddRecipeRequest
                .builder()
                .name("soup")
                .servings(4)
                .instructions("boil for 40 minutes")
                .ingredients(ingredients)
                .build();

        when().i_add_a_new_recipe(addRecipeRequest);

        then()
                .recipe_has_an_id()
                .and()
                .ingredients_have_ids()
                .and()
                .recipe_has_name(addRecipeRequest.getName())
                .and()
                .recipe_has_servings(addRecipeRequest.getServings())
                .and()
                .recipe_has_instructions(addRecipeRequest.getInstructions())
                .and()
                .recipe_has_ingredient(lentils)
                .and()
                .recipe_has_ingredient(tomato);
    }

    @JGivenStage
    public static class GivenStage extends Stage<GivenStage> {

    }

    @JGivenStage
    public static class WhenStage extends Stage<WhenStage> {

        @Autowired
        private RestTestClient restTestClient;

        @ProvidedScenarioState
        private AddRecipeResponse addRecipeResponse;

        public WhenStage i_add_a_new_recipe(AddRecipeRequest addRecipeRequest) throws Exception {

            restTestClient
                    .post()
                    .uri(AddRecipeApi.PATH_ADD)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(addRecipeRequest)
                    .exchange()
                    .expectBody(AddRecipeResponse.class)
                    .value(arg -> addRecipeResponse = arg);
            return self();
        }
    }

    @JGivenStage
    public static class ThenStage extends Stage<ThenStage> {

        @ExpectedScenarioState
        private AddRecipeResponse addRecipeResponse;

        public ThenStage recipe_has_an_id() {

            assertThat(addRecipeResponse).isNotNull();
            assertThat(addRecipeResponse.getId()).isNotNull();
            return self();
        }

        public ThenStage ingredients_have_ids() {

            assertThat(addRecipeResponse).isNotNull();
            boolean anyNullId = addRecipeResponse
                    .getIngredients()
                    .stream()
                    .map(Ingredient::getId)
                    .anyMatch(Objects::isNull);
            assertThat(anyNullId).isFalse();
            return self();
        }

        public ThenStage recipe_has_name(String name) {

            assertThat(addRecipeResponse).isNotNull();
            assertThat(addRecipeResponse.getName()).isEqualTo(name);
            return self();
        }

        public ThenStage recipe_has_servings(int servings) {

            assertThat(addRecipeResponse).isNotNull();
            assertThat(addRecipeResponse.getServings()).isEqualTo(servings);
            return self();
        }

        public ThenStage recipe_has_instructions(String instructions) {

            assertThat(addRecipeResponse).isNotNull();
            assertThat(addRecipeResponse.getInstructions()).isEqualTo(instructions);
            return self();
        }

        public ThenStage recipe_has_ingredient(Ingredient ingredient) {

            assertThat(addRecipeResponse).isNotNull();
            assertThat(addRecipeResponse.getIngredients()).anyMatch(arg -> ingredient
                    .getName()
                    .equals(arg.getName()) && ingredient
                    .getIsVegetarian()
                    .equals(arg.getIsVegetarian()));
            return self();
        }
    }
}
