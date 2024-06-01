package com.devpaik.payment.domain.payment.field;

import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Fee implements Serializable {
    @Getter
    private final BigDecimal value;

    public Fee(BigDecimal value) {
        this.value = value;
    }


    public static Fee createFee(String fee) {
        return new Fee(new BigDecimal(fee));
    }

    public Fee calculate(Amount amount) {
        return new Fee(this.value.multiply(amount.getValue()));
    }

    public BigDecimal scaleValue() {
        return this.value.setScale(2, RoundingMode.FLOOR);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fee that = (Fee) o;
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
