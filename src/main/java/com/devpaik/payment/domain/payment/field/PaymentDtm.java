package com.devpaik.payment.domain.payment.field;

import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class PaymentDtm implements Serializable {
    @Getter
    private final LocalDateTime datetime;

    public PaymentDtm(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentDtm that = (PaymentDtm) o;
        return Objects.equals(datetime, that.datetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(datetime);
    }

    @Override
    public String toString() {
        return String.valueOf(datetime);
    }
}
