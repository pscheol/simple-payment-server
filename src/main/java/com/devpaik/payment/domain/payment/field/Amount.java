package com.devpaik.payment.domain.payment.field;

import com.devpaik.payment.domain.exchangerate.field.CurrencyCode;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Amount implements Serializable {
    @Getter
    private final BigDecimal value;


    public Amount(BigDecimal value) {
        this.value = value;
    }

    public Amount(Double value) {
        this.value = new BigDecimal(value);
    }

    public Amount sum(BigDecimal value) {
        return new Amount(this.value.add(value));
    }

    public BigDecimal scaleValue(CurrencyCode currencyCode) {
        if (CurrencyCode.KRW.equals(currencyCode.getValue())) {
            return this.value.setScale(0, RoundingMode.FLOOR);
        } else {
            return this.value.setScale(2, RoundingMode.FLOOR);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amount that = (Amount) o;
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
