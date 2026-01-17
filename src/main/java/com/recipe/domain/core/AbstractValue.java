package com.recipe.domain.core;

public abstract class AbstractValue<T> {

    private final T value;

    protected AbstractValue(T value) {
        this.value = value;
    }

    public T value() {

        return value;
    }
}
