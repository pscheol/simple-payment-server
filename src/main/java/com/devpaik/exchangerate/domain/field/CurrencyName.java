package com.devpaik.exchangerate.domain.field;

import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

public class CurrencyName implements Serializable {
    @Getter
    private final String value;

    private CurrencyName() {
        this(null);
    }

    public CurrencyName(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyName that = (CurrencyName) o;
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
