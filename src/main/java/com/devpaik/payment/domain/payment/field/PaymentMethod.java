package com.devpaik.payment.domain.payment.field;

public enum PaymentMethod {
    creditCard, point;


    public boolean isPoint() {
        return point.equals(this);
    }

    public boolean isCreditCard() {
        return creditCard.equals(this);
    }
}
