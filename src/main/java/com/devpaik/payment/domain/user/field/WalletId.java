package com.devpaik.payment.domain.user.field;

import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

public class WalletId implements Serializable {
    @Getter
    private final String id;

    public WalletId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WalletId that = (WalletId) o;
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
