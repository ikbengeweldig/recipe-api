package com.recipe.domain.core;

import java.util.Objects;
import java.util.UUID;

public class IngredientId extends AbstractValue<UUID> {

    public IngredientId(UUID value) {
        super(value);
        Objects.requireNonNull(value, "id cannot be null");
    }
}
