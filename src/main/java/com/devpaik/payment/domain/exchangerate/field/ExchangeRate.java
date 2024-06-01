package com.devpaik.payment.domain.exchangerate.field;

import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class ExchangeRate implements Serializable {
    @Getter
    private final BigDecimal value;

    private ExchangeRate() {
        this(null);
    }

    public ExchangeRate(BigDecimal value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRate that = (ExchangeRate) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
