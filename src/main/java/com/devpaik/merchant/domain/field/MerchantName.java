package com.devpaik.merchant.domain.field;

import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

public class MerchantName implements Serializable {
    @Getter
    private final String value;


    private MerchantName() {
        this(null);
    }

    public MerchantName(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MerchantName that = (MerchantName) o;
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
