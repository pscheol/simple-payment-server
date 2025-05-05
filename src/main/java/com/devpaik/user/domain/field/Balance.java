package com.devpaik.user.domain.field;

import com.devpaik.exchangerate.domain.field.CurrencyCode;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Balance implements Serializable {

    @Getter
    private final BigDecimal value;

    public Balance(BigDecimal value) {
        this.value = value;
    }


    public BigDecimal scaleValue(CurrencyCode currencyCode) {
        if (CurrencyCode.KRW.equals(currencyCode.getValue())) {
            return value.setScale(0, RoundingMode.FLOOR);
        }
        return value.setScale(2, RoundingMode.FLOOR);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Balance that = (Balance) o;
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
