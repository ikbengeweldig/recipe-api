package com.recipe.adapter.in.rest;

import com.recipe.api.model.AddRecipeRequest;
import com.recipe.api.model.AddRecipeResponse;
import com.recipe.api.model.Ingredient;
import com.recipe.api.model.Recipe;
import com.recipe.api.model.RetrieveRecipeResponse;
import com.recipe.api.rest.AddRecipeApi;
import com.recipe.api.rest.DeleteRecipeApi;
import com.recipe.api.rest.RetrieveRecipeApi;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.integration.spring.EnableJGiven;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import com.tngtech.jgiven.integration.spring.junit5.SpringScenarioTest;
import org.junit.jupiter.api.AfterAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.test.web.servlet.client.StatusAssertions;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@EnableJGiven
@Testcontainers
@SpringBootTest
@AutoConfigureRestTestClient
@ComponentScan(includeFilters = @ComponentScan.Filter(value = JGivenStage.class))
public abstract class AbstractTestBase<G extends AbstractTestBase.GivenStage, W extends AbstractTestBase.WhenStage, T extends AbstractTestBase.ThenStage> extends SpringScenarioTest<G, W, T> {

    protected static final Ingredient ING_LENTILS = Ingredient
            .builder()
            .name("lentils")
            .isVegetarian(true)
            .build();
    protected static final Ingredient ING_TOMATO = Ingredient
            .builder()
            .name("tomato")
            .isVegetarian(true)
            .build();

    protected static final AddRecipeRequest REC_LENTILS_SOUP = AddRecipeRequest
            .builder()
            .name("lentils soup")
            .servings(4)
            .instructions("boil for 40 minutes")
            .ingredients(List.of(ING_LENTILS, ING_TOMATO))
            .build();

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:18.1");

    @AfterAll
    static void afterAll() {

        postgres.stop();
    }

    public static class GivenStage<G extends AbstractTestBase.GivenStage<G>> extends Stage<G> {

        @ProvidedScenarioState
        private AddRecipeRequest addRecipeRequest;

        public G i_have_a_recipe_to_add(AddRecipeRequest addRecipeRequest) {

            this.addRecipeRequest = addRecipeRequest;
            return self();
        }
    }

    public static class WhenStage<W extends AbstractTestBase.WhenStage<W>> extends Stage<W> {

        @Autowired
        private RestTestClient restTestClient;

        @ExpectedScenarioState
        private AddRecipeRequest addRecipeRequest;

        @ProvidedScenarioState
        private Recipe recipe;

        @ProvidedScenarioState
        private StatusAssertions statusAssertions;

        @ProvidedScenarioState
        private RestTestClient.ResponseSpec lastRetrieveResponse;

        public W i_add_my_new_recipe() {

            restTestClient
                    .post()
                    .uri(AddRecipeApi.PATH_ADD)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(addRecipeRequest)
                    .exchange()
                    .expectBody(AddRecipeResponse.class)
                    .value(arg -> recipe = arg);
            return self();
        }

        public W i_retrieve_my_recipe_with_id() {

            lastRetrieveResponse = restTestClient
                    .get()
                    .uri(RetrieveRecipeApi.PATH_RETRIEVE, recipe.getId())
                    .exchange();
            return self();
        }

        public W the_recipe_is_returned_successfully() {

            lastRetrieveResponse
                    .expectStatus()
                    .isOk()
                    .expectBody(RetrieveRecipeResponse.class)
                    .value(arg -> recipe = arg);

            return self();
        }

        public W the_recipe_is_not_found() {

            lastRetrieveResponse
                    .expectStatus()
                    .isNotFound();
            return self();
        }

        public W i_delete_my_recipe() {

            statusAssertions = restTestClient
                    .delete()
                    .uri(DeleteRecipeApi.PATH_DELETE, recipe.getId())
                    .exchange()
                    .expectStatus();
            return self();
        }
    }

    public static class ThenStage<T extends AbstractTestBase.ThenStage<T>> extends Stage<T> {

        @ExpectedScenarioState
        protected Recipe recipe;

        @ExpectedScenarioState
        private StatusAssertions statusAssertions;

        public T recipe_has_an_id() {

            assertThat(recipe).isNotNull();
            assertThat(recipe.getId()).isNotNull();
            return self();
        }

        public T ingredients_have_ids() {

            assertThat(recipe).isNotNull();
            boolean anyNullId = recipe
                    .getIngredients()
                    .stream()
                    .map(Ingredient::getId)
                    .anyMatch(Objects::isNull);
            assertThat(anyNullId).isFalse();
            return self();
        }

        public T recipe_has_name(String name) {

            assertThat(recipe).isNotNull();
            assertThat(recipe.getName()).isEqualTo(name);
            return self();
        }

        public T recipe_has_servings(int servings) {

            assertThat(recipe).isNotNull();
            assertThat(recipe.getServings()).isEqualTo(servings);
            return self();
        }

        public T recipe_has_instructions(String instructions) {

            assertThat(recipe).isNotNull();
            assertThat(recipe.getInstructions()).isEqualTo(instructions);
            return self();
        }

        public T recipe_has_ingredient(Ingredient ingredient) {

            assertThat(recipe).isNotNull();
            assertThat(recipe.getIngredients()).anyMatch(arg -> ingredient
                    .getName()
                    .equals(arg.getName()) && ingredient
                    .getIsVegetarian()
                    .equals(arg.getIsVegetarian()));
            return self();
        }

        public T http_status_is(HttpStatus httpStatus) {

            statusAssertions.isEqualTo(httpStatus);

            return self();
        }
    }
}
