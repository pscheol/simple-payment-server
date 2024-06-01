package com.devpaik.payment.domain.payment.field;

import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

public class CardNum implements Serializable {
    @Getter
    private final String value;

    public CardNum(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardNum cardNum = (CardNum) o;
        return Objects.equals(value, cardNum.value);
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
