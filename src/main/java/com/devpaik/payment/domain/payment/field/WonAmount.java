package com.devpaik.payment.domain.payment.field;

import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

public class WonAmount implements Serializable {
    @Getter
    private final Long value;

    public WonAmount(Long value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WonAmount wonAmount = (WonAmount) o;
        return Objects.equals(value, wonAmount.value);
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
