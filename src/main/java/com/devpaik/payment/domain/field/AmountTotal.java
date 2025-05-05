package com.devpaik.payment.domain.field;

import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class AmountTotal implements Serializable {
    @Getter
    private final BigDecimal value;

    public AmountTotal(BigDecimal value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AmountTotal that = (AmountTotal) o;
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
