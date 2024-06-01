package com.devpaik.payment.domain.payment.field;

import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

public class PaymentId implements Serializable {
    @Getter
    private final String id;

    public PaymentId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentId paymentId = (PaymentId) o;
        return Objects.equals(id, paymentId.id);
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
