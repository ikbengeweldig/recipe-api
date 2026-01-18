package com.recipe.adapter.in.rest;

import com.tngtech.jgiven.integration.spring.EnableJGiven;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import com.tngtech.jgiven.integration.spring.junit5.SpringScenarioTest;
import org.junit.jupiter.api.AfterAll;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.ComponentScan;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

@EnableJGiven
@Testcontainers
@SpringBootTest
@AutoConfigureRestTestClient
@ComponentScan(includeFilters = @ComponentScan.Filter(value = JGivenStage.class))
public abstract class AbstractTestBase<G, W, T> extends SpringScenarioTest<G, W, T> {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:18.1");

    @AfterAll
    static void afterAll() {

        postgres.stop();
    }
}
