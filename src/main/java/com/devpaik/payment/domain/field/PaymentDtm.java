package com.devpaik.payment.domain.field;

import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

public class PaymentDtm implements Serializable {
    @Getter
    private final ZonedDateTime datetime;

    public PaymentDtm(ZonedDateTime datetime) {
        this.datetime = datetime;
    }

    public static PaymentDtm nowDtm(ZoneId zoneId) {
        return new PaymentDtm(LocalDateTime.now().atZone(zoneId));
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
