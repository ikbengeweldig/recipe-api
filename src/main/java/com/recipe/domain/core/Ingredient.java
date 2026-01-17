package com.recipe.domain.core;

import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
public record Ingredient(UUID id, String name, Boolean isVegetarian) {

    public Ingredient {

        if (Objects.isNull(name) || name.length() == 0 || name.replaceAll("\\s+", "").length() == 0) {
            throw new IllegalArgumentException("this ingredient must have a name");
        }

        Objects.requireNonNull(isVegetarian, "must specify if this ingredient is vegetarian or not");
    }
}
