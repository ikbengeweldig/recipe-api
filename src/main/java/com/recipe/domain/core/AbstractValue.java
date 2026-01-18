package com.recipe.domain.core;

import java.util.Objects;

import static java.util.Optional.ofNullable;

public abstract class AbstractValue<T> {

    private final T value;

    protected AbstractValue(T value) {
        this.value = value;
    }

    public T value() {

        return value;
    }

    @Override
    public String toString() {

        return ofNullable(value)
                .map(Objects::toString)
                .orElse(null);
    }
}
