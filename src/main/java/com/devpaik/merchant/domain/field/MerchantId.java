package com.devpaik.merchant.domain.field;

import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

public class MerchantId implements Serializable {
    @Getter
    private final String id;


    private MerchantId() {
        this(null);
    }

    public MerchantId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MerchantId that = (MerchantId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
