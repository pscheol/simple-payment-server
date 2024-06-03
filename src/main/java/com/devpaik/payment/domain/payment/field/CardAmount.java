package com.devpaik.payment.domain.payment.field;

import com.devpaik.payment.domain.exchangerate.field.CurrencyCode;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class CardAmount implements Serializable {
    @Getter
    private final BigDecimal value;

    public CardAmount(BigDecimal value) {
        this.value = value;
    }

    public static CardAmount calculateSubtract(WalletAmount walletAmount, AmountTotal amountTotal, CurrencyCode currencyCode) {
        if (CurrencyCode.KRW.equals(currencyCode.getValue())) {
            return new CardAmount(amountTotal.getValue().subtract(walletAmount.getValue()).setScale(0, RoundingMode.FLOOR));
        }
        return new CardAmount(amountTotal.getValue().subtract(walletAmount.getValue()).setScale(2, RoundingMode.FLOOR));
    }

    public static CardAmount create(BigDecimal value) {
        return new CardAmount(value);
    }

    public static CardAmount createZero() {
        return new CardAmount(BigDecimal.ZERO);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardAmount that = (CardAmount) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public boolean isNotZero() {
        return !(this.value.equals(BigDecimal.ZERO));
    }
}
