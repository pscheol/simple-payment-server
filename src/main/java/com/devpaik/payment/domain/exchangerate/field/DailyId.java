package com.devpaik.payment.domain.exchangerate.field;

import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

public class DailyId implements Serializable {
    @Getter
    private final Long id;


    private DailyId() {
        this(null);
    }

    public DailyId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyId that = (DailyId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
