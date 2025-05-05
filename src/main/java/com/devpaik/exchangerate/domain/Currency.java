package com.devpaik.exchangerate.domain;

import com.devpaik.exchangerate.domain.field.CurrencyCode;
import com.devpaik.exchangerate.domain.field.CurrencyName;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

public class Currency implements Serializable {

    @Getter
    private final CurrencyCode currencyCode;

    @Getter
    private final CurrencyName currencyName;

    private Currency(CurrencyCode currencyCode, CurrencyName currencyName) {
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return Objects.equals(currencyCode, currency.currencyCode) && Objects.equals(currencyName, currency.currencyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currencyCode, currencyName);
    }

    @Override
    public String toString() {
        return "Currency{" +
                "currencyCode=" + currencyCode +
                ", currencyName=" + currencyName +
                '}';
    }
}
