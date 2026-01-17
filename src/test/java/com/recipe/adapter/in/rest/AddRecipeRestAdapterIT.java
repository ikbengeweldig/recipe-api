package com.recipe.adapter.in.rest;

import com.recipe.api.model.AddRecipeRequest;
import com.recipe.api.model.AddRecipeResponse;
import com.recipe.api.model.Ingredient;
import com.recipe.api.rest.AddRecipeApi;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.integration.spring.EnableJGiven;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import com.tngtech.jgiven.integration.spring.junit5.SpringScenarioTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@EnableJGiven
@Testcontainers
@SpringBootTest
@AutoConfigureRestTestClient
@ComponentScan(includeFilters = @ComponentScan.Filter(value = JGivenStage.class))
public class AddRecipeRestAdapterIT extends SpringScenarioTest<AddRecipeRestAdapterIT.GivenStage, AddRecipeRestAdapterIT.WhenStage, AddRecipeRestAdapterIT.ThenStage> {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:18.1");

    @Test
    void successful_add_recipe() throws Exception {

        List<Ingredient> ingredients = List.of(Ingredient
                .builder()
                .name("lentils")
                .isVegetarian(true)
                .build(), Ingredient
                .builder()
                .name("tomato")
                .isVegetarian(true)
                .build());
        AddRecipeRequest addRecipeRequest = AddRecipeRequest
                .builder()
                .name("soup")
                .servings(4)
                .instructions("boil for 40 minutes")
                .ingredients(ingredients)
                .build();
        when().i_add_a_new_recipe(addRecipeRequest);
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
    }
}
