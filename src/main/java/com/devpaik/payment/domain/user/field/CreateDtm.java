package com.devpaik.payment.domain.user.field;

import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class CreateDtm implements Serializable {

    @Getter
    private final LocalDateTime datetime;

    public CreateDtm(LocalDateTime datetime) {
        this.datetime = datetime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateDtm that = (CreateDtm) o;
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
