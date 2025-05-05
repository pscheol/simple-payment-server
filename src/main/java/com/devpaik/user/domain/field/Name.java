package com.devpaik.user.domain.field;

import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

public class Name implements Serializable {
    @Getter
    private final String value;

    public Name(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name that = (Name) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
