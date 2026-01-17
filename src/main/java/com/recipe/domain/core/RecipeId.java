package com.recipe.domain.core;

import java.util.Objects;
import java.util.UUID;

public class RecipeId extends AbstractValue<UUID> {

    protected RecipeId(UUID value) {
        super(value);
        Objects.requireNonNull(value, "id cannot be null");
    }
}
