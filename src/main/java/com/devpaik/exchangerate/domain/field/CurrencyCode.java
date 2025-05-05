package com.devpaik.exchangerate.domain.field;

import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

public class CurrencyCode implements Serializable {
    public static final String KRW = "KRW";
    public static final String USD = "USD";
    @Getter
    private final String value;

    public CurrencyCode(String value) {
        this.value = value;
    }



    public static CurrencyCode createCurrencyCode(Code code) {
        return new CurrencyCode(code.name());
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyCode that = (CurrencyCode) o;
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

    public enum Code {
        KRW, USD
    }
}
